package com.qcadoo.mes.core.data.definition.view;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qcadoo.mes.core.data.beans.Entity;
import com.qcadoo.mes.core.data.definition.DataDefinition;

public abstract class RootContainerDefinition extends ContainerDefinition<Object> {

    private final Map<String, ComponentDefinition<?>> componentRegistry = new LinkedHashMap<String, ComponentDefinition<?>>();

    public RootContainerDefinition(final String name, final DataDefinition dataDefinition) {
        super(name, null, null, null);
        setDataDefinition(dataDefinition);
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    public void initialize() {
        registerComponents(getComponents());
        initializeComponents(0);
    }

    private void initializeComponents(final int previousNotInitialized) {
        int notInitialized = 0;

        for (ComponentDefinition<?> component : componentRegistry.values()) {
            if (component.isInitialized()) {
                continue;
            }
            if (!component.initializeComponent(componentRegistry)) {
                notInitialized++;
            }
        }

        if (notInitialized > 0) {
            if (previousNotInitialized == notInitialized) {
                for (ComponentDefinition<?> component : componentRegistry.values()) {
                    if (component.isInitialized()) {
                        continue;
                    }
                }
                throw new IllegalStateException("Cyclic dependency in view definition");
            } else {
                initializeComponents(notInitialized);
            }
        }
    }

    private void registerComponents(final Map<String, ComponentDefinition<?>> components) {
        for (ComponentDefinition<?> component : components.values()) {
            componentRegistry.put(component.getPath(), component);
            if (component instanceof ContainerDefinition) {
                registerComponents(((ContainerDefinition<?>) component).getComponents());
            }

        }
    }

    @Override
    public Object castContainerValue(final Entity entity, final Object viewObject) {
        return null;
    }

    @Override
    public Object getContainerValue(final Entity entity, final Map<String, Entity> selectedEntities,
            final ViewEntity<Object> globalViewEntity, final ViewEntity<Object> viewEntity) {
        return null;
    }

}
