/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 1.2.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.mes.samples.loader;

import static com.qcadoo.mes.samples.constants.SamplesConstants.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jdom.Element;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.qcadoo.mes.samples.constants.SamplesConstants;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.NumberService;
import com.qcadoo.model.api.search.SearchRestrictions;

@Component
@Transactional
public class TestSamplesLoader extends MinimalSamplesLoader {

    private static final Logger LOG = LoggerFactory.getLogger(TestSamplesLoader.class);

    private static final long MILLIS_IN_DAY = 86400000;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private NumberService numberService;

    private Map<String, Entity> operationComponents = new LinkedHashMap<String, Entity>();

    @Override
    protected void loadData(final String locale) {
        final String dataset = "test";

        readDataFromXML(dataset, "users", locale);

        readDataFromXML(dataset, "dictionaries", locale);
        readDataFromXML(dataset, L_COMPANY, locale);
        readDataFromXML(dataset, "defaultParameters", locale);
        readDataFromXML(dataset, L_WORKSTATION_TYPES, locale);
        if (isEnabledOrEnabling(WAGE_GROUPS_PLUGIN_IDENTIFIER)) {
            readDataFromXML(dataset, L_WAGE_GROUP, locale);
        }
        readDataFromXML(dataset, BASIC_MODEL_STAFF, locale);
        readDataFromXML(dataset, "conversionItem", locale);
        readDataFromXML(dataset, PRODUCTS_PLUGIN_IDENTIFIER, locale);
        readDataFromXML(dataset, L_SHIFTS, locale);
        readDataFromXML(dataset, "divisions", locale);

        if (isEnabledOrEnabling(TECHNOLOGIES_PLUGIN_IDENTIFIER)) {
            readDataFromXML(dataset, "operations", locale);
            readDataFromXML(dataset, TECHNOLOGIES_PLUGIN_IDENTIFIER, locale);
            readDataFromXML(dataset, "technologyOperComp", locale);
            readDataFromXML(dataset, "operationProductInComp", locale);
            readDataFromXML(dataset, "operationProductOutComp", locale);
        }

        if (isEnabledOrEnabling(PRODUCTION_LINES_PLUGIN_IDENTIFIER)) {
            readDataFromXML(dataset, L_PRODUCTION_LINES, locale);
            readDataFromXML(dataset, L_DEFAULT_PRODUCTION_LINE, locale);
        }

        if (isEnabledOrEnabling(ORDERS_PLUGIN_IDENTIFIER)) {
            readDataFromXML(dataset, ORDERS_PLUGIN_IDENTIFIER, locale);
        }

        if (isEnabledOrEnabling(L_COST_CALCULATION)) {
            readDataFromXML(dataset, L_COST_CALCULATION, locale);
        }

        if (isEnabledOrEnabling(L_MATERIAL_FLOW)) {
            readDataFromXML(dataset, L_LOCATION, locale);
            readDataFromXML(dataset, L_RESOURCE, locale);
            readDataFromXML(dataset, L_TRANSFORMATIONS, locale);
            readDataFromXML(dataset, L_TRANSFER, locale);
            readDataFromXML(dataset, L_STOCK_CORRECTION, locale);
        }

        if (isEnabledOrEnabling(L_QUALITY_CONTROLS)) {
            readDataFromXML(dataset, L_QUALITY_CONTROLS, locale);
        }

        if (isEnabledOrEnabling(L_MATERIAL_REQUIREMENTS)) {
            readDataFromXML(dataset, L_MATERIAL_REQUIREMENTS, locale);
        }

        if (isEnabledOrEnabling(L_WORK_PLANS)) {
            readDataFromXML(dataset, L_WORK_PLANS, locale);
        }

        if (isEnabledOrEnabling(L_PRODUCTION_COUNTING)) {
            readDataFromXML(dataset, L_PRODUCTION_RECORD, locale);
            readDataFromXML(dataset, RECORDOPERATIONPRODUCTINCOMPONENT_MODEL_RECORDOPERATIONPRODUCTINCOMPONENT, locale);
            readDataFromXML(dataset, RECORDOPERATIONPRODUCTOUTCOMPONENT_MODEL_RECORDOPERATIONPRODUCTOUTCOMPONENT, locale);
            // readDataFromXML(dataset, L_PRODUCTION_COUNTING, locale);
            // readDataFromXML(dataset, L_PRODUCTION_BALANCE, locale);
        }

        if (isEnabledOrEnabling(L_ADVANCED_GENEALOGY)) {
            readDataFromXML(dataset, L_BATCHES, locale);
            if (isEnabledOrEnabling(L_ADVANCED_GENEALOGY_FOR_ORDERS)) {
                readDataFromXML(dataset, L_TRACKING_RECORDS, locale);
                readDataFromXML(dataset, "usedBatches", locale);
            }
            readDataFromXML(dataset, L_GENEALOGY_TABLES, locale);
        }

        readDataFromXML(dataset, L_COMPANY_PRODUCTS_FAMILY, locale);

        if (isEnabledOrEnabling(L_SUPPLY_NEGOTIATIONS)) {
            readDataFromXML(dataset, L_NEGOTIATIONS, locale);
            readDataFromXML(dataset, L_NEGOTIATION_PRODUCTS, locale);
            readDataFromXML(dataset, L_NEGOTIATION_STATE_CHANGE, locale);
            readDataFromXML(dataset, L_REQUEST_FOR_QUOTION, locale);
            readDataFromXML(dataset, L_REQUEST_FOR_QUOTION_PRODUCTS, locale);
            readDataFromXML(dataset, L_REQUEST_FOR_QUOTION_STATE_CHANGE, locale);
            readDataFromXML(dataset, L_OFFER, locale);
            readDataFromXML(dataset, L_OFFER_PRODUCTS, locale);
            readDataFromXML(dataset, L_OFFER_STATE_CHANGES, locale);

        }

        if (isEnabledOrEnabling(L_DELIVERY)) {
            readDataFromXML(dataset, L_DELIVERY_DELIVERY, locale);
            readDataFromXML(dataset, L_ORDERED_PRODUCTS, locale);
            readDataFromXML(dataset, L_DELIVERY_STATE_CHANGES, locale);
        }

    }

    @Override
    protected void readData(final Map<String, String> values, final String type, final Element node) {
        super.readData(values, type, node);

        if (PRODUCTS_PLUGIN_IDENTIFIER.equals(type)) {
            addProduct(values);
        } else if (ORDERS_PLUGIN_IDENTIFIER.equals(type)) {
            prepareTechnologiesForOrder(values);
            addOrder(values);
            changedOrderState(values);
        } else if (TECHNOLOGIES_PLUGIN_IDENTIFIER.equals(type)) {
            addTechnology(values);
        } else if ("technologyOperComp".equals(type)) {
            addOperationComponent(values);
        } else if ("operationProductInComp".equals(type)) {
            addProductInComponent(values);
        } else if ("operationProductOutComp".equals(type)) {
            addProductOutComponent(values);
        } else if ("operations".equals(type)) {
            addOperations(values);
        } else if (BASIC_MODEL_STAFF.equals(type)) {
            addStaff(values);
        } else if (L_WORKSTATION_TYPES.equals(type)) {
            addWorkstationType(values);
        } else if (L_DIVISION.equals(type)) {
            addDivision(values);
        } else if (L_COST_CALCULATION.equals(type)) {
            addCostCalculation(values);
        } else if (L_LOCATION.equals(type)) {
            addLocation(values);
        } else if (L_RESOURCE.equals(type)) {
            addResource(values);
        } else if (L_TRANSFORMATIONS.equals(type)) {
            addTransformation(values);
        } else if (L_TRANSFER.equals(type)) {
            addTransfer(values);
        } else if (L_STOCK_CORRECTION.equals(type)) {
            addStockCorrection(values);
        } else if (L_BATCHES.equals(type)) {
            addBatches(values);
        } else if (L_TRACKING_RECORDS.equals(type)) {
            addTrackingRecord(values);
        } else if ("usedBatches".equals(type)) {
            addUsedBatch(values);
        } else if (L_GENEALOGY_TABLES.equals(type)) {
            addGenealogyTables(values);
        } else if (L_QUALITY_CONTROLS.equals(type)) {
            // addQualityControl(values);
        } else if (L_MATERIAL_REQUIREMENTS.equals(type)) {
            addMaterialRequirements(values);
        } else if (L_WORK_PLANS.equals(type)) {
            // addWorkPlan(values);
        } else if (L_PRODUCTION_RECORD.equals(type)) {
            addProductionRecord(values);
        } else if (RECORDOPERATIONPRODUCTINCOMPONENT_MODEL_RECORDOPERATIONPRODUCTINCOMPONENT.equals(type)) {
            addRecordOperationProductInComponent(values);
        } else if (RECORDOPERATIONPRODUCTOUTCOMPONENT_MODEL_RECORDOPERATIONPRODUCTOUTCOMPONENT.equals(type)) {
            addRecordOperationProductOutComponent(values);
        } else if (L_PRODUCTION_COUNTING.equals(type)) {
            prepareProductionRecords(values);
            addProductionCounting(values);
        } else if (L_PRODUCTION_BALANCE.equals(type)) {
            addProductionBalance(values);
        } else if (L_WAGE_GROUP.equals(type)) {
            addWageGroups(values);
        } else if (L_COMPANY_PRODUCTS_FAMILY.equals(type)) {
            addCompanyProductsFamily(values);
        } else if (L_NEGOTIATIONS.equals(type)) {
            addNegotiations(values);
            changeddNegotiationState(values);
        } else if (L_NEGOTIATION_PRODUCTS.equals(type)) {
            addNegotiationProducts(values);
        } else if (L_REQUEST_FOR_QUOTION.equals(type)) {
            addRequestForQuotations(values);
            changedRequestForQuotationState(values);
        } else if (L_REQUEST_FOR_QUOTION_PRODUCTS.equals(type)) {
            addRequestForQuotationProducts(values);
        } else if (L_OFFER.equals(type)) {
            addOffers(values);
            changedOfferState(values);
        } else if (L_OFFER_PRODUCTS.equals(type)) {
            addOfferProducts(values);
        } else if (L_DELIVERY_DELIVERY.equals(type)) {
            addDeliveries(values);
            changedDeliveryState(values);
        } else if (L_ORDERED_PRODUCTS.equals(type)) {
            addOrderProducts(values);
        } else if (L_DELIVERY_STATE_CHANGES.equals(type)) {
            addDeliveryStateChange(values);
        } else if (L_NEGOTIAITION_STATE_CHANGES.equals(type)) {
            addNegotiationStateChange(values);
        } else if (L_OFFER_STATE_CHANGES.equals(type)) {
            addOfferStateChange(values);
        } else if (L_REQUEST_FOR_QUOTION_STATE_CHANGE.equals(type)) {
            addRequestForQuotationStateChange(values);
        }
    }

    private void addRequestForQuotationStateChange(final Map<String, String> values) {
        long date = System.currentTimeMillis();
        Entity requestForQuotationStateChange = dataDefinitionService
                .get(L_SUPPLY_NEGOTIATIONS, "requestForQuotationStateChange").create();
        requestForQuotationStateChange.setField("dateAndTime", new Date(date));
        requestForQuotationStateChange.setField("sourceState", values.get("sourcestate"));
        requestForQuotationStateChange.setField("targetState", values.get("targetstate"));
        requestForQuotationStateChange.setField("status", values.get("status"));
        requestForQuotationStateChange.setField("phase", values.get("phase"));
        requestForQuotationStateChange.setField("worker", values.get("worker"));
        requestForQuotationStateChange.setField(REQUEST_FOR_QUOTION,
                getRequestForQuotationByNumber(values.get("requestforquotation")));
        requestForQuotationStateChange.setField(L_SHIFT, getShiftByName(values.get(L_SHIFT)));
        requestForQuotationStateChange.getDataDefinition().save(requestForQuotationStateChange);

    }

    private void addOfferStateChange(final Map<String, String> values) {
        long date = System.currentTimeMillis();
        Entity offerStateChange = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, "offerStateChange").create();
        offerStateChange.setField("dateAndTime", new Date(date));
        offerStateChange.setField("sourceState", values.get("sourcestate"));
        offerStateChange.setField("targetState", values.get("targetstate"));
        offerStateChange.setField("status", values.get("status"));
        offerStateChange.setField("phase", values.get("phase"));
        offerStateChange.setField("worker", values.get("worker"));
        offerStateChange.setField(OFFER, getOfferByNumber(values.get(OFFER)));
        offerStateChange.setField(L_SHIFT, getShiftByName(values.get(L_SHIFT)));
        offerStateChange.getDataDefinition().save(offerStateChange);

    }

    private void addNegotiationStateChange(final Map<String, String> values) {
        long date = System.currentTimeMillis();
        Entity negotiationStateChange = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, "negotiationStateChange").create();
        negotiationStateChange.setField("dateAndTime", new Date(date));
        negotiationStateChange.setField("sourceState", values.get("sourcestate"));
        negotiationStateChange.setField("targetState", values.get("targetstate"));
        negotiationStateChange.setField("status", values.get("status"));
        negotiationStateChange.setField("phase", values.get("phase"));
        negotiationStateChange.setField("worker", values.get("worker"));
        negotiationStateChange.setField(NEGOTIATION, getNegotationByNumber(values.get(NEGOTIATION)));
        negotiationStateChange.setField(L_SHIFT, getShiftByName(values.get(L_SHIFT)));
        negotiationStateChange.getDataDefinition().save(negotiationStateChange);

    }

    private void addDeliveryStateChange(final Map<String, String> values) {
        long date = System.currentTimeMillis();
        Entity deliveryStateChange = dataDefinitionService.get(L_DELIVERY, "deliveryStateChange").create();
        deliveryStateChange.setField("dateAndTime", new Date(date));
        deliveryStateChange.setField("sourceState", values.get("sourcestate"));
        deliveryStateChange.setField("targetState", values.get("targetstate"));
        deliveryStateChange.setField("status", values.get("status"));
        deliveryStateChange.setField("phase", values.get("phase"));
        deliveryStateChange.setField("worker", values.get("worker"));
        deliveryStateChange.setField(DELIVERY, getDeliveryByNumber(values.get(DELIVERY)));
        deliveryStateChange.setField(L_SHIFT, getShiftByName(values.get(L_SHIFT)));
        deliveryStateChange.getDataDefinition().save(deliveryStateChange);
    }

    private void addOrderProducts(final Map<String, String> values) {
        Entity orderedProduct = dataDefinitionService.get(L_DELIVERY, "orderedProduct").create();
        orderedProduct.setField(DELIVERY, getDeliveryByNumber(values.get(DELIVERY)));
        orderedProduct.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT_NR)));
        orderedProduct.setField("orderedQuantity", values.get("orderedquantity"));
        orderedProduct.setField("description", values.get("description"));
        orderedProduct.setField("pricePerUnit", values.get("priceperunit"));
        orderedProduct.setField("totalPrice", values.get("totalprice"));
        if (isEnabledOrEnabling(L_SUPPLY_NEGOTIATIONS)) {
            orderedProduct.setField("offer", getOfferByNumber(values.get("offer")));
        }
        orderedProduct.getDataDefinition().save(orderedProduct);
    }

    private void addDeliveries(final Map<String, String> values) {
        Entity delivery = dataDefinitionService.get(L_DELIVERY, DELIVERY).create();
        delivery.setField(L_NUMBER, values.get(L_NUMBER));
        delivery.setField(L_NAME, values.get(L_NAME));
        delivery.setField(L_DESCRIPTION, values.get(L_DESCRIPTION));
        delivery.setField(L_SUPPLIER, getSupplierByNumber(values.get(L_SUPPLIER)));
        delivery.setField("deliveryAddress", values.get("deliveryaddress"));
        delivery.setField(L_STATE, values.get(L_STATE));
        delivery.setField("deliveryDate", values.get("deliverydate"));
        delivery.setField("externalSynchronized", true);
        delivery.getDataDefinition().save(delivery);

    }

    private void addOfferProducts(final Map<String, String> values) {
        Entity offerProduct = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, "offerProduct").create();
        offerProduct.setField(OFFER, getOfferByNumber(values.get(OFFER)));
        offerProduct.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT_NR)));
        offerProduct.setField(L_QUANTITY, values.get(L_QUANTITY));
        offerProduct.setField("pricePerUnit", values.get("priceperunit"));
        offerProduct.setField("totalPrice", values.get("totalprice"));
        offerProduct.getDataDefinition().save(offerProduct);

    }

    private void addOffers(final Map<String, String> values) {
        Entity offer = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, OFFER).create();
        offer.setField(L_NUMBER, values.get(L_NUMBER));
        offer.setField(L_NAME, values.get(L_NAME));
        offer.setField(L_SUPPLIER, getSupplierByNumber(values.get(L_SUPPLIER)));
        offer.setField("offeredDate", values.get("offereddate"));
        offer.setField(NEGOTIATION, getNegotationByNumber(values.get(NEGOTIATION)));
        offer.setField(L_STATE, values.get(L_STATE));
        offer.setField(REQUEST_FOR_QUOTION, getRequestForQuotationByNumber(values.get("requestforquotation")));
        offer.setField("transportCost", values.get("transportcost"));
        offer.getDataDefinition().save(offer);

    }

    private void addRequestForQuotationProducts(final Map<String, String> values) {
        Entity requestForQuotationProduct = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, "requestForQuotationProduct")
                .create();
        requestForQuotationProduct.setField(REQUEST_FOR_QUOTION,
                getRequestForQuotationByNumber(values.get("requestforquotation")));
        requestForQuotationProduct.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT_NR)));
        requestForQuotationProduct.setField("orderedQuantity", values.get("orderedquantity"));
        requestForQuotationProduct.setField("annualVolume", values.get("annualvolume"));
        requestForQuotationProduct.getDataDefinition().save(requestForQuotationProduct);
    }

    private void addRequestForQuotations(final Map<String, String> values) {
        Entity requestForQuotation = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, REQUEST_FOR_QUOTION).create();
        requestForQuotation.setField(L_NUMBER, values.get(L_NUMBER));
        requestForQuotation.setField(L_NAME, values.get(L_NAME));
        requestForQuotation.setField(L_SUPPLIER, getSupplierByNumber(values.get(L_SUPPLIER)));
        requestForQuotation.setField("desiredDate", values.get("desireddate"));
        requestForQuotation.setField(NEGOTIATION, getNegotationByNumber(values.get(NEGOTIATION)));
        requestForQuotation.setField(L_STATE, values.get(L_STATE));
        requestForQuotation.getDataDefinition().save(requestForQuotation);

    }

    private void addNegotiationProducts(final Map<String, String> values) {
        Entity negotiationProduct = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, "negotiationProduct").create();
        negotiationProduct.setField(NEGOTIATION, getNegotationByNumber(values.get(NEGOTIATION)));
        negotiationProduct.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT_NR)));
        negotiationProduct.setField("neededQuantity", values.get("neededquantity"));
        negotiationProduct.setField("approvedDeliveredQuantity", values.get("approveddeliveredquantity"));
        negotiationProduct.setField("approvedDeliveredQuantity", values.get("approveddeliveredquantity"));
        negotiationProduct.setField("leftQuantity", values.get("leftquantity"));
        negotiationProduct.setField("dueDate", values.get("duedate"));
        negotiationProduct.setField("requestForQuotationsNumber", values.get("requestforquotationsnumber"));
        negotiationProduct.getDataDefinition().save(negotiationProduct);
    }

    private void addNegotiations(final Map<String, String> values) {
        Entity negotation = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, NEGOTIATION).create();
        negotation.setField(L_NUMBER, values.get(L_NUMBER));
        negotation.setField(L_NAME, values.get(L_NAME));
        negotation.setField("farthestLimitDate", values.get(L_FARTHESTLIMITDATE));
        negotation.setField(L_STATE, values.get(L_STATE));
        negotation.setField("includedCompanies", Lists.newArrayList(getCompany("2"), getCompany("3")));
        negotation.getDataDefinition().save(negotation);

    }

    private void changeddNegotiationState(final Map<String, String> values) {
        String state = values.get(L_STATE);
        if (STATE_DRAFT.equals(state)) {
            return;
        }
        Entity negotiation = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, "negotiation").find()
                .add(SearchRestrictions.eq(L_NUMBER, values.get(L_NUMBER))).uniqueResult();
        negotiation.setField(L_STATE, state);
        negotiation.getDataDefinition().save(negotiation);
    }

    private void changedRequestForQuotationState(final Map<String, String> values) {
        String state = values.get(L_STATE);
        if (STATE_DRAFT.equals(state)) {
            return;
        }
        Entity requestForQuotation = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, REQUEST_FOR_QUOTION).find()
                .add(SearchRestrictions.eq(L_NUMBER, values.get(L_NUMBER))).uniqueResult();
        requestForQuotation.setField(L_STATE, state);
        requestForQuotation.getDataDefinition().save(requestForQuotation);
    }

    private void changedOfferState(final Map<String, String> values) {
        String state = values.get(L_STATE);
        if (STATE_DRAFT.equals(state)) {
            return;
        }
        Entity offer = dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, OFFER).find()
                .add(SearchRestrictions.eq(L_NUMBER, values.get(L_NUMBER))).uniqueResult();
        offer.setField(L_STATE, state);
        offer.getDataDefinition().save(offer);
    }

    private void changedDeliveryState(final Map<String, String> values) {
        String state = values.get(L_STATE);
        if (STATE_DRAFT.equals(state)) {
            return;
        }
        Entity delivery = dataDefinitionService.get(L_DELIVERY, DELIVERY).find()
                .add(SearchRestrictions.eq(L_NUMBER, values.get(L_NUMBER))).uniqueResult();
        delivery.setField(L_STATE, state);
        delivery.getDataDefinition().save(delivery);
    }

    private void addCompanyProductsFamily(final Map<String, String> values) {
        Entity companyProductsFamily = dataDefinitionService.get("deliveries", L_COMPANY_PRODUCTS_FAMILY).create();

        companyProductsFamily.setField(L_COMPANY, getCompany(values.get(L_COMPANY)));
        companyProductsFamily.setField("product", getProductByNumber(values.get("product")));

        dataDefinitionService.get("deliveries", L_COMPANY_PRODUCTS_FAMILY).save(companyProductsFamily);
    }

    private void addWorkstationType(final Map<String, String> values) {
        Entity machine = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_WORKSTATION_TYPE).create();

        LOG.debug("id: " + values.get("id") + " name " + values.get(L_NAME) + " prod_line " + values.get("prod_line")
                + " description " + values.get(L_DESCRIPTION));
        machine.setField(L_NUMBER, values.get("id"));
        machine.setField(L_NAME, values.get(L_NAME));
        machine.setField(L_DESCRIPTION, values.get(L_DESCRIPTION));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test machine item {machine=" + machine.getField(L_NAME) + ", " + L_NUMBER + "="
                    + machine.getField(L_NUMBER) + "}");
        }

        dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_WORKSTATION_TYPE).save(machine);
    }

    private void addStaff(final Map<String, String> values) {
        Entity staff = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_STAFF).create();

        LOG.debug("id: " + values.get("id") + " name " + values.get(L_NAME) + " " + L_SURNAME + " " + values.get(L_SURNAME)
                + " post " + values.get("post") + " wage_group " + values.get("wage_group"));
        staff.setField(L_NUMBER, values.get("id"));
        staff.setField(L_NAME, values.get(L_NAME));
        staff.setField(L_SURNAME, values.get(L_SURNAME));
        staff.setField("post", values.get("post"));

        if (isEnabledOrEnabling(WAGE_GROUPS_PLUGIN_IDENTIFIER)) {
            Entity wageGroup = getWageGroup(values.get("wage_group"));
            staff.setField(L_WAGE_GROUP, wageGroup);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test staff item {staff=" + staff.getField(L_NAME) + ", " + L_SURNAME + "= "
                    + staff.getField(L_SURNAME) + ", " + L_WAGE_GROUP + "= " + staff.getField(L_WAGE_GROUP) + "}");
        }
        dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_STAFF).save(staff);
    }

    private void addOperations(final Map<String, String> values) {
        Entity operation = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, TECHNOLOGY_MODEL_OPERATION).create();

        operation.setField(L_NAME, values.get(L_NAME));
        operation.setField(L_NUMBER, values.get(L_NUMBER));

        if (isEnabledOrEnabling("timeNormsForOperations")) {
            operation.setField(L_TPZ, values.get(L_TPZ));
            operation.setField("tj", values.get("tj"));
            operation.setField("productionInOneCycle", values.get("productioninonecycle"));
            operation.setField("nextOperationAfterProducedType", values.get("nextoperationafterproducedtype"));
            operation.setField("machineUtilization", values.get("machineutilization"));
            operation.setField("laborUtilization", values.get("laborutilization"));
            operation.setField("nextOperationAfterProducedQuantity", values.get("nextoperationafterproducedquantity"));
            operation.setField("timeNextOperation", values.get("timenextoperation"));
            operation.setField("areProductQuantitiesDivisible", false);
            operation.setField("isTjDivisible", false);
            operation.setField("productionInOneCycleUNIT", values.get("productioninonecycleunit"));
        }
        operation.setField(BASIC_MODEL_WORKSTATION_TYPE, getMachine(values.get(L_NUMBER)));
        operation.setField(BASIC_MODEL_STAFF, getRandomStaff());
        if (isEnabledOrEnabling(L_TECH_SUBCONTRACTING) && !values.get("operationcompanies").isEmpty()) {
            operation.setField("operationCompanies", Lists.newArrayList(getCompany(values.get("operationcompanies"))));
        }
        if (isEnabledOrEnabling("costNormsForOperation")) {
            operation.setField("pieceworkCost", values.get("pieceworkcost"));
            operation.setField("machineHourlyCost", values.get("machinehourlycost"));
            operation.setField(L_LABOR_HOURLY_COST, values.get("laborhourlycost"));
            operation.setField("numberOfOperations", values.get("numberofoperations"));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test operation item {name=" + operation.getField(L_NAME) + ", " + L_NUMBER + "="
                    + operation.getField(L_NUMBER) + "}");
        }
        dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, TECHNOLOGY_MODEL_OPERATION).save(operation);
    }

    private void addProduct(final Map<String, String> values) {
        Entity product = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_PRODUCT).create();
        product.setField("category", getRandomDictionaryItem("categories"));
        if (!values.get(L_EAN).isEmpty()) {
            product.setField(L_EAN, values.get(L_EAN));
        }
        if (!values.get(L_NAME).isEmpty()) {
            product.setField(L_NAME, values.get(L_NAME));
        }
        if (!values.get(L_BATCH).isEmpty()) {
            product.setField(L_BATCH, values.get(L_BATCH));
        }
        if (!values.get(L_PRODUCT_NR).isEmpty()) {
            product.setField(L_NUMBER, values.get(L_PRODUCT_NR));
        }
        if (!values.get(L_TYPE_OF_PRODUCT).isEmpty()) {
            product.setField(L_GLOBAL_TYPE_OF_MATERIAL, values.get(L_TYPE_OF_PRODUCT));
        }
        if (!values.get(L_PARENT).isEmpty()) {
            product.setField(L_PARENT, getProductByNumber(values.get(L_PARENT)));
        }
        product.setField(L_ENTITY_TYPE, values.get("entitytype"));
        product.setField(L_UNIT, values.get(L_UNIT));

        if (isEnabledOrEnabling("costNormsForProduct")) {
            product.setField("costForNumber", values.get("costfornumber"));
            product.setField("nominalCost", values.get("nominalcost"));
            product.setField("lastPurchaseCost", values.get("lastpurchasecost"));
            product.setField("averageCost", values.get("averagecost"));
        }

        product = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_PRODUCT).save(product);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test product {id=" + product.getId() + ", category=" + product.getField("category") + ", ean="
                    + product.getField(L_EAN) + ", name=" + product.getField(L_NAME) + ", " + L_NUMBER + "="
                    + product.getField(L_NUMBER) + ", globalTypeOfMaterial=" + product.getField("typeOfMaterial") + ", unit="
                    + product.getField(L_UNIT) + "}");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < RANDOM.nextInt(5); i++) {
            for (int j = 0; j <= i; j++) {
                stringBuilder.append("#");
            }
            addSubstitute(values.get(L_NAME) + stringBuilder.toString(), values.get(L_PRODUCT_NR) + stringBuilder.toString(),
                    product, i + 1);
        }
    }

    private void addSubstitute(final String name, final String number, final Entity product, final int priority) {
        Entity substitute = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_SUBSTITUTE).create();
        substitute.setField(L_NAME, name);
        substitute.setField(L_NUMBER, number);
        substitute.setField("priority", priority);
        substitute.setField(BASIC_MODEL_PRODUCT, product);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test substitute {name=" + substitute.getField(L_NAME) + ", " + L_NUMBER + "="
                    + substitute.getField(L_NUMBER) + ", priority=" + substitute.getField("priority") + ", subsitute product="
                    + ((Entity) substitute.getField(BASIC_MODEL_PRODUCT)).getField(L_NUMBER) + "}");
        }

        substitute = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_SUBSTITUTE).save(substitute);

        for (int i = 0; i < 1; i++) {
            addSubstituteComponent(substitute, getRandomProduct(), 100 * RANDOM.nextDouble());
        }
    }

    private void addSubstituteComponent(final Entity substitute, final Entity product, final double quantity) {
        Entity substituteComponent = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, "substituteComponent").create();
        substituteComponent.setField(BASIC_MODEL_PRODUCT, product);
        substituteComponent.setField(L_QUANTITY, numberService.setScale(new BigDecimal(quantity)));
        substituteComponent.setField(BASIC_MODEL_SUBSTITUTE, substitute);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test substitute component {substitute="
                    + ((Entity) substituteComponent.getField(BASIC_MODEL_SUBSTITUTE)).getField(L_NUMBER) + ", subsitute product="
                    + ((Entity) substituteComponent.getField(BASIC_MODEL_PRODUCT)).getField(L_NUMBER) + ", quantity="
                    + substituteComponent.getField(L_QUANTITY) + "}");
        }

        dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, "substituteComponent").save(substituteComponent);
    }

    private void prepareTechnologiesForOrder(final Map<String, String> values) {
        final Entity technology = getTechnologyByNumber(values.get("tech_nr"));
        if (STATE_ACCEPTED.equals(technology.getStringField(L_STATE))) {
            return;
        }
        getStateChangeSamplesClient().changeState(technology, STATE_ACCEPTED);
    }

    private void addOrder(final Map<String, String> values) {
        long startDate = System.currentTimeMillis();
        long endDate = startDate;
        long deadline = startDate;
        long millsInHour = 3600000;
        long millsInMinute = 60000;

        if (!values.get("scheduled_start_date").isEmpty()) {
            try {
                startDate = getDateFormat().parse(values.get("scheduled_start_date")).getTime();
            } catch (ParseException e) {
                LOG.warn(e.getMessage(), e);
            }
        }
        startDate = startDate + Long.valueOf(values.get("delay_started_date")) * 3600000;
        endDate = startDate + (RANDOM.nextInt(1) + 1) * MILLIS_IN_DAY + (RANDOM.nextInt(9) + 1) * millsInHour
                + (RANDOM.nextInt(40) + 35) * millsInMinute;

        deadline = endDate;

        if (!values.get("scheduled_end_date").isEmpty()) {
            try {
                endDate = getDateFormat().parse(values.get("scheduled_end_date")).getTime();
            } catch (ParseException e) {
                LOG.warn(e.getMessage(), e);
            }
        }

        if (!values.get("deadline").isEmpty()) {
            try {
                deadline = getDateFormat().parse(values.get("deadline")).getTime();
            } catch (ParseException e) {
                LOG.warn(e.getMessage(), e);
            }
        }

        Entity order = dataDefinitionService.get(ORDERS_PLUGIN_IDENTIFIER, ORDERS_MODEL_ORDER).create();
        order.setField(L_DATE_FROM, new Date(startDate));
        order.setField(L_DATE_TO, new Date(endDate));
        order.setField(L_DEADLINE, new Date(deadline));
        order.setField("externalSynchronized", true);

        Entity technology = getTechnologyByNumber(values.get("tech_nr"));
        order.setField("technologyPrototype", technology);
        order.setField(L_NAME,
                (values.get(L_NAME).isEmpty() || values.get(L_NAME) == null) ? values.get(L_ORDER_NR) : values.get(L_NAME));
        order.setField(L_NUMBER, values.get(L_ORDER_NR));
        order.setField(L_PLANNED_QUANTITY, values.get("quantity_scheduled").isEmpty() ? new BigDecimal(
                100 * RANDOM.nextDouble() + 1) : new BigDecimal(values.get("quantity_scheduled")));

        order.setField(L_PRODUCTION_LINE, getProductionLineByNumber(values.get("production_line_nr")));

        Entity product = getProductByNumber(values.get(L_PRODUCT_NR));

        if (isEnabledOrEnabling(L_PRODUCTION_COUNTING)) {
            order.setField("typeOfProductionRecording", values.get("type_of_production_recording"));
            order.setField("registerQuantityInProduct", values.get("register_quantity_in_product"));
            order.setField("registerQuantityOutProduct", values.get("register_quantity_out_product"));
            order.setField("registerProductionTime", values.get("register_production_time"));
            order.setField("registerPiecework", values.get("register_piecework"));
            order.setField("justOne", values.get("just_one"));
            order.setField("allowToClose", values.get("allow_to_close"));
            order.setField("autoCloseOrder", values.get("auto_close_order"));
        }

        if (isEnabledOrEnabling(L_ADVANCED_GENEALOGY_FOR_ORDERS)) {
            order.setField("trackingRecordTreatment", "01duringProduction");
            order.setField("trackingRecordForOrderTreatment", values.get("tracking_record_for_order_treatment"));
        }

        order.setField(BASIC_MODEL_PRODUCT, product);
        if (order.getField(TECHNOLOGY_MODEL_TECHNOLOGY) == null) {
            order.setField(TECHNOLOGY_MODEL_TECHNOLOGY, getDefaultTechnologyForProduct(product));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test order {id="
                    + order.getId()
                    + ", name="
                    + order.getField(L_NAME)
                    + ", "
                    + L_NUMBER
                    + "="
                    + order.getField(L_NUMBER)
                    + ", order product="
                    + (order.getField(BASIC_MODEL_PRODUCT) == null ? null : ((Entity) order.getField(BASIC_MODEL_PRODUCT))
                            .getField(L_NUMBER))
                    + ", technology="
                    + (order.getField(TECHNOLOGY_MODEL_TECHNOLOGY) == null ? null : ((Entity) order
                            .getField(TECHNOLOGY_MODEL_TECHNOLOGY)).getField(L_NUMBER)) + ", dateFrom="
                    + order.getField(L_DATE_FROM) + ", dateTo=" + order.getField(L_DATE_TO) + ", effectiveDateFrom="
                    + order.getField("effectiveDateFrom") + ", effectiveDateTo=" + order.getField("effectiveDateTo")
                    + ", doneQuantity=" + order.getField("doneQuantity") + ", plannedQuantity="
                    + order.getField(L_PLANNED_QUANTITY) + ", trackingRecordTreatment="
                    + order.getField("trackingRecordTreatment") + ", state=" + order.getField(L_ORDER_STATE) + "}");
        }

        order.setField("orderType", "01withPatternTechnology");
        dataDefinitionService.get(ORDERS_PLUGIN_IDENTIFIER, ORDERS_MODEL_ORDER).save(order);
    }

    private void changedOrderState(final Map<String, String> values) {
        String state = values.get(L_STATE);
        if (ORDER_STATE_PENDING.equals(state)) {
            return;
        }
        Entity order = dataDefinitionService.get(ORDERS_PLUGIN_IDENTIFIER, ORDERS_MODEL_ORDER).find()
                .add(SearchRestrictions.eq(L_NUMBER, values.get(L_ORDER_NR))).uniqueResult();
        if (ORDER_STATE_IN_PROGRESS.equals(state)) {
            order = getStateChangeSamplesClient().changeState(order, STATE_ACCEPTED);
        }
        getStateChangeSamplesClient().changeState(order, state);
        Date effectiveDateFrom = order.getDateField("effectiveDateFrom");
        if (effectiveDateFrom != null) {
            effectiveDateFrom = (new DateTime(effectiveDateFrom).plusDays(Integer.valueOf(values.get("day_plus_effective_date")
                    .toString()))).toDate();
            order.setField("effectiveDateFrom", effectiveDateFrom);
            dataDefinitionService.get(ORDERS_PLUGIN_IDENTIFIER, ORDERS_MODEL_ORDER).save(order);
        }
    }

    private void addBatches(final Map<String, String> values) {
        Entity batch = dataDefinitionService.get(L_ADVANCED_GENEALOGY, L_BATCH).create();

        batch.setField(L_NUMBER, values.get(L_NUMBER));
        batch.setField(L_PRODUCT, getProductByNumber(values.get("product_nr")));
        batch.setField(L_SUPPLIER, getSupplierByNumber(values.get("supplier_nr")));
        batch.setField(L_STATE, BATCH_STATE_TRACKED);

        batch.getDataDefinition().save(batch);
    }

    private void addTrackingRecord(final Map<String, String> values) {
        Entity trackingRecord = dataDefinitionService.get(L_ADVANCED_GENEALOGY, "trackingRecord").create();
        trackingRecord.setField(L_ENTITY_TYPE, values.get("entity_type"));
        trackingRecord.setField(L_NUMBER, values.get(L_NUMBER));
        trackingRecord.setField("producedBatch", getBatchByNumber(values.get("produced_batch_no")));
        trackingRecord.setField(L_ORDER, getOrderByNumber(values.get("order_no")));
        trackingRecord.setField(L_STATE, STATE_DRAFT);
        trackingRecord.getDataDefinition().save(trackingRecord);
    }

    private void addUsedBatch(final Map<String, String> values) {
        Entity genealogyProductInBatch = dataDefinitionService.get(L_ADVANCED_GENEALOGY_FOR_ORDERS, "genealogyProductInBatch")
                .create();

        genealogyProductInBatch.setField(L_BATCH, getBatchByNumber(values.get("batch")));
        Entity trackingRecord = dataDefinitionService.get(L_ADVANCED_GENEALOGY, "trackingRecord").find()
                .add(SearchRestrictions.eq(L_NUMBER, values.get("trackingrecord"))).uniqueResult();
        Entity genealogyProductInComponent = addGenealogyProductInComponent(trackingRecord, values.get(BASIC_MODEL_PRODUCT),
                values.get(TECHNOLOGY_MODEL_OPERATION));
        genealogyProductInBatch.setField("genealogyProductInComponent", genealogyProductInComponent);
        genealogyProductInBatch.getDataDefinition().save(genealogyProductInBatch);
    }

    private Entity addGenealogyProductInComponent(final Entity trackingRecord, final String productNumber,
            final String operationNumber) {
        Entity product = getProductByNumber(productNumber);
        Entity order = trackingRecord.getBelongsToField(ORDERS_MODEL_ORDER);
        Entity technology = order.getBelongsToField(TECHNOLOGY_MODEL_TECHNOLOGY);
        Entity operationProdInComp = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, "operationProductInComponent")
                .find().add(SearchRestrictions.belongsTo(L_PRODUCT, product)).setMaxResults(1).uniqueResult();
        Entity technologyInstanceOperationComponent = dataDefinitionService
                .get(SamplesConstants.TECHNOLOGIES_PLUGIN_IDENTIFIER,
                        SamplesConstants.TECHNOLOGY_MODEL_TECHNOLOGY_INSTANCE_OPERATION_COMPONENT)
                .find()
                .add(SearchRestrictions.and(SearchRestrictions.belongsTo(ORDERS_MODEL_ORDER, order),
                        SearchRestrictions.belongsTo(TECHNOLOGY_MODEL_TECHNOLOGY, technology),
                        SearchRestrictions.belongsTo(L_OPERATION, getOperationByNumber(operationNumber)))).setMaxResults(1)
                .uniqueResult();

        Entity genealogyProductInComponent = dataDefinitionService
                .get(L_ADVANCED_GENEALOGY_FOR_ORDERS, "genealogyProductInComponent")
                .find()
                .add(SearchRestrictions.and(SearchRestrictions.belongsTo("trackingRecord", trackingRecord), SearchRestrictions
                        .belongsTo("productInComponent", operationProdInComp), SearchRestrictions.belongsTo(
                        SamplesConstants.TECHNOLOGY_MODEL_TECHNOLOGY_INSTANCE_OPERATION_COMPONENT,
                        technologyInstanceOperationComponent))).setMaxResults(1).uniqueResult();
        return genealogyProductInComponent;
    }

    private void addGenealogyTables(final Map<String, String> values) {
        Entity genealogyTable = dataDefinitionService.get(L_ADVANCED_GENEALOGY, "genealogyReport").create();

        genealogyTable.setField(L_TYPE, values.get(L_TYPE));
        genealogyTable.setField(L_NAME, values.get(L_NAME));
        genealogyTable.setField("includeDraft", values.get("include_draft"));
        genealogyTable.setField("directRelatedOnly", values.get("direct_related_only"));
        genealogyTable.setField(L_BATCH, getBatchByNumber(values.get("batch_no")));

        genealogyTable.getDataDefinition().save(genealogyTable);
    }

    private void addDivision(final Map<String, String> values) {
        Entity division = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, L_DIVISION).create();

        division.setField(L_NUMBER, values.get("NUMBER"));
        division.setField(L_NAME, values.get("NAME"));
        division.setField("supervisor", values.get("SUPERVISOR"));

        division.getDataDefinition().save(division);
    }

    private void addCostCalculation(final Map<String, String> values) {
        Entity costCalculation = dataDefinitionService.get(L_COST_CALCULATION, L_COST_CALCULATION).create();

        costCalculation.setField(L_NUMBER, values.get(L_NUMBER));
        costCalculation.setField(L_DESCRIPTION, values.get(L_DESCRIPTION));
        costCalculation.setField(ORDERS_MODEL_ORDER, getOrderByNumber(values.get("orderno")));
        costCalculation.setField(TECHNOLOGY_MODEL_TECHNOLOGY, getTechnologyByNumber(values.get("techno")));
        costCalculation.setField("defaultTechnology", getTechnologyByNumber(values.get("deftechno")));
        costCalculation.setField(L_PRODUCT, getProductByNumber(values.get("prodno")));
        costCalculation.setField(L_QUANTITY, values.get(L_QUANTITY));
        costCalculation.setField("includeTPZ", values.get("includetpz"));
        costCalculation.setField("sourceOfMaterialCosts", values.get("sourceofmaterialcosts"));
        costCalculation.setField("calculateMaterialCostsMode", values.get("calculatematerialcostmode"));
        costCalculation.setField("calculateOperationCostsMode", values.get("calculateoperationcostmode"));
        costCalculation.setField("additionalOverhead", values.get("additionaloverhead"));
        costCalculation.setField("productionCostMargin", values.get("productioncostmargin"));
        costCalculation.setField("materialCostMargin", values.get("materialcostmargin"));
        costCalculation.setField("productionLine", getProductionLineByNumber(values.get("productionlines")));
        costCalculation.getDataDefinition().save(costCalculation);
    }

    private void addLocation(final Map<String, String> values) {
        Entity location = dataDefinitionService.get(L_MATERIAL_FLOW, L_LOCATION).create();

        location.setField(L_NUMBER, values.get(L_NUMBER));
        location.setField(L_NAME, values.get(L_NAME));

        if ("02warehouse".equals(values.get(L_TYPE))) {
            if (isEnabledOrEnabling("materialFlowResources")) {
                location.setField(L_TYPE, values.get(L_TYPE));
                location.getDataDefinition().save(location);
            }
        } else {
            location.setField(L_TYPE, values.get(L_TYPE));
            location.getDataDefinition().save(location);

        }

    }

    private void addResource(final Map<String, String> values) {
        Entity resource = dataDefinitionService.get(L_MATERIAL_FLOW_RESOURCES, L_RESOURCE).create();

        resource.setField(L_LOCATION, getLocationByNumber(values.get(L_LOCATION)));
        resource.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT)));
        resource.setField(L_QUANTITY, values.get(L_QUANTITY));
        resource.setField(L_TIME, values.get(L_TIME));

        resource.getDataDefinition().save(resource);
    }

    private void addTransformation(final Map<String, String> values) {
        Entity transformation = dataDefinitionService.get(L_MATERIAL_FLOW, L_TRANSFORMATIONS).create();

        transformation.setField(L_NUMBER, values.get(L_NUMBER));
        transformation.setField(L_NAME, values.get(L_NAME));
        transformation.setField(L_TIME, values.get(L_TIME));
        transformation.setField(L_LOCATION_FROM, getLocationByNumber(values.get("location_from")));
        transformation.setField(L_LOCATION_TO, getLocationByNumber(values.get("location_to")));
        transformation.setField(L_STAFF, getStaffByNumber(values.get(L_STAFF)));

        transformation.getDataDefinition().save(transformation);
    }

    private void addStockCorrection(final Map<String, String> values) {
        Entity stockCorrection = dataDefinitionService.get(L_MATERIAL_FLOW, L_STOCK_CORRECTION).create();

        stockCorrection.setField(L_NUMBER, values.get(L_NUMBER));
        stockCorrection.setField("stockCorrectionDate", values.get("stock_correction_date"));
        stockCorrection.setField(L_LOCATION, getLocationByNumber(values.get(L_LOCATION)));
        stockCorrection.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT)));
        stockCorrection.setField(L_STAFF, getStaffByNumber(values.get(L_STAFF)));
        stockCorrection.setField("found", values.get("found"));

        stockCorrection.getDataDefinition().save(stockCorrection);
    }

    private void addTransfer(final Map<String, String> values) {
        Entity transfer = dataDefinitionService.get(L_MATERIAL_FLOW, L_TRANSFER).create();

        transfer.setField(L_NUMBER, values.get(L_NUMBER));
        transfer.setField(L_TYPE, values.get(L_TYPE));
        transfer.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT)));
        transfer.setField(L_QUANTITY, values.get(L_QUANTITY));
        transfer.setField(L_STAFF, getStaffByNumber(values.get(L_STAFF)));
        transfer.setField(L_LOCATION_FROM, getLocationByNumber(values.get("location_from")));
        transfer.setField(L_LOCATION_TO, getLocationByNumber(values.get("location_to")));
        transfer.setField(L_TIME, values.get(L_TIME));

        transfer.setField("transformationsConsumption", getTransformationByNumber(values.get("transformations_consumption")));
        transfer.setField("transformationsProduction", getTransformationByNumber(values.get("transformations_production")));

        transfer.getDataDefinition().save(transfer);
    }

    private void addTechnology(final Map<String, String> values) {
        Entity product = getProductByNumber(values.get(L_PRODUCT_NR));

        if (product != null) {
            final DataDefinition technologyDD = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER,
                    TECHNOLOGY_MODEL_TECHNOLOGY);
            final Entity technology = technologyDD.create();
            if (!values.get(L_DESCRIPTION).isEmpty()) {
                technology.setField(L_DESCRIPTION, values.get(L_DESCRIPTION));
            }
            technology.setField("master", false);
            technology.setField(L_NAME, values.get(L_NAME));
            technology.setField(L_NUMBER, values.get("bom_nr"));
            technology.setField(BASIC_MODEL_PRODUCT, product);
            technology.setField(L_DESCRIPTION, values.get("DESCRIPTION"));
            technology.setField("batchRequired", true);
            technology.setField("postFeatureRequired", false);
            technology.setField("otherFeatureRequired", false);
            technology.setField("shiftFeatureRequired", false);
            technology.setField("technologyBatchRequired", false);

            if (isEnabledOrEnabling(L_QUALITY_CONTROLS_FOR_OPERATION)
                    && L_QUALITY_CONTROLS_FOR_OPERATION.equals(values.get(L_QUALITYCONTROLTYPE_3))) {
                technology.setField(L_QUALITY_CONTROL_TYPE2, L_QUALITY_CONTROLS_FOR_OPERATION);
            }

            if (!(isEnabledOrEnabling(L_QUALITY_CONTROLS_FOR_OPERATION) && "04forOperation".equals(values
                    .get(L_QUALITY_CONTROL_TYPE)))
                    && isEnabledOrEnabling(L_QUALITY_CONTROLS)
                    && ("02forUnit".equals(values.get(L_QUALITY_CONTROL_TYPE)) || "03forOrder".equals(values
                            .get(L_QUALITY_CONTROL_TYPE)))) {
                technology.setField(L_QUALITY_CONTROL_TYPE2, values.get(L_QUALITY_CONTROL_TYPE));
                if ("02forUnit".equals(values.get(L_QUALITY_CONTROL_TYPE))) {
                    technology.setField("unitSamplingNr", values.get("unit_sampling_nr"));
                }
            }

            if (!values.get("minimal").isEmpty()) {
                technology.setField("minimalQuantity", values.get("minimal"));
            }

            if (isEnabledOrEnabling(SamplesConstants.L_GOOD_FOOD)) {
                technology.setField("formula", "123");
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Add test technology {id=" + technology.getId() + ", name=" + technology.getField(L_NAME) + ", "
                        + L_NUMBER + "=" + technology.getField(L_NUMBER) + ", technology product="
                        + ((Entity) technology.getField(BASIC_MODEL_PRODUCT)).getField(L_NUMBER) + ", description="
                        + technology.getField(L_DESCRIPTION) + ", master=" + technology.getField("master") + "}");
            }

            technologyDD.save(technology);
        }
    }

    private void addRecordOperationProductInComponent(final Map<String, String> values) {
        DataDefinition productInComponentDD = dataDefinitionService.get(SamplesConstants.PRODUCTION_COUNTING_PLUGIN_IDENTIFIER,
                SamplesConstants.RECORDOPERATIONPRODUCTINCOMPONENT_MODEL_RECORDOPERATIONPRODUCTINCOMPONENT);

        Entity productInComponent = productInComponentDD.find()
                .add(SearchRestrictions.belongsTo(BASIC_MODEL_PRODUCT, getProductByNumber(values.get(BASIC_MODEL_PRODUCT))))
                .setMaxResults(1).uniqueResult();

        if (productInComponent != null) {
            productInComponent.setField("usedQuantity", values.get("usedquantity"));
            productInComponent.setField(L_BALANCE, values.get(L_BALANCE));
            productInComponentDD.save(productInComponent);
        }
    }

    private void addRecordOperationProductOutComponent(final Map<String, String> values) {
        DataDefinition productOutComponentDD = dataDefinitionService.get(SamplesConstants.PRODUCTION_COUNTING_PLUGIN_IDENTIFIER,
                SamplesConstants.RECORDOPERATIONPRODUCTOUTCOMPONENT_MODEL_RECORDOPERATIONPRODUCTOUTCOMPONENT);

        Entity productOutComponent = productOutComponentDD.find()
                .add(SearchRestrictions.belongsTo(BASIC_MODEL_PRODUCT, getProductByNumber(values.get(BASIC_MODEL_PRODUCT))))
                .setMaxResults(1).uniqueResult();

        if (productOutComponent != null) {
            productOutComponent.setField("usedQuantity", values.get("usedquantity"));
            productOutComponent.setField(L_BALANCE, values.get(L_BALANCE));

            productOutComponentDD.save(productOutComponent);
        }
    }

    private void addOperationComponent(final Map<String, String> values) {
        DataDefinition techOperCompDD = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, "technologyOperationComponent");
        Entity component = techOperCompDD.create();
        Entity technology = getTechnologyByNumber(values.get("technology_nr"));
        component.setField(TECHNOLOGY_MODEL_TECHNOLOGY, technology);
        Entity parent = operationComponents.get(values.get(L_PARENT));
        component.setField(L_PARENT, parent);
        Entity operation = getOperationByNumber(values.get("operation_nr"));
        component.setField(TECHNOLOGY_MODEL_OPERATION, operation);
        component.setField(L_ENTITY_TYPE, values.get("entity_type"));
        if (isEnabledOrEnabling("timeNormsForOperations")) {
            component.setField(L_TPZ, operation.getField(L_TPZ));
            component.setField("tj", operation.getField("tj"));
            component.setField("machineUtilization", operation.getField("machineUtilization"));
            component.setField("laborUtilization", operation.getField("laborUtilization"));
            component.setField("productionInOneCycle", operation.getField("productionInOneCycle"));
            component.setField("productionInOneCycleUNIT", values.get("productioninonecycleunit"));
            component.setField("nextOperationAfterProducedType", operation.getField("nextOperationAfterProducedType"));
            component.setField("nextOperationAfterProducedQuantity", operation.getField("nextOperationAfterProducedQuantity"));
            component.setField("areProductQuantitiesDivisible", operation.getField("areProductQuantitiesDivisible"));
            component.setField("isTjDivisible", operation.getField("isTjDivisible"));
            component.setField("timeNextOperation", operation.getField("timeNextOperation"));
        }

        if (isEnabledOrEnabling("costNormsForOperation")) {
            component.setField("pieceworkCost", operation.getField("pieceworkCost"));
            component.setField("machineHourlyCost", operation.getField("machineHourlyCost"));
            component.setField(L_LABOR_HOURLY_COST, operation.getField(L_LABOR_HOURLY_COST));
            component.setField("numberOfOperations", operation.getField("numberOfOperations"));
        }
        if (isEnabledOrEnabling(L_TECH_SUBCONTRACTING) && !values.get("issubcontracting").isEmpty()) {
            component.setField("isSubcontracting", true);
        }

        component = techOperCompDD.save(component);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test operation component {technology="
                    + ((Entity) component.getField(TECHNOLOGY_MODEL_TECHNOLOGY)).getField(L_NUMBER) + ", parent="
                    + (parent == null || parent.getId() == null ? 0 : parent.getId()) + ", operation="
                    + ((Entity) component.getField(TECHNOLOGY_MODEL_OPERATION)).getField(L_NUMBER) + "}");
        }
        operationComponents.put(values.get(L_BOM_ID), component);
    }

    private void addProductInComponent(final Map<String, String> values) {
        Entity productComponent = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, "operationProductInComponent")
                .create();
        final Entity operationComponent = operationComponents.get(values.get("operation_comp_id"));
        productComponent.setField(L_OPERATION_COMPONENT, operationComponent);
        productComponent.setField(L_QUANTITY, values.get(L_QUANTITY));
        productComponent.setField(BASIC_MODEL_PRODUCT, getProductByNumber(values.get(L_PRODUCT_NR)));
        productComponent.setField("batchRequired", false);
        productComponent.setField("productBatchRequired", false);

        productComponent = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, "operationProductInComponent").save(
                productComponent);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test product component {product="
                    + ((Entity) productComponent.getField(BASIC_MODEL_PRODUCT)).getField(L_NUMBER)
                    + ", operation="
                    + ((Entity) ((Entity) productComponent.getField(SamplesConstants.L_OPERATION_COMPONENT))
                            .getField(TECHNOLOGY_MODEL_OPERATION)).getField(L_NUMBER) + ", quantity="
                    + productComponent.getField(L_QUANTITY) + "}");
        }
    }

    private void addProductOutComponent(final Map<String, String> values) {
        Entity productComponent = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, "operationProductOutComponent")
                .create();
        productComponent.setField(L_OPERATION_COMPONENT, operationComponents.get(values.get("operation_comp_id")));
        productComponent.setField(L_QUANTITY, values.get(L_QUANTITY));
        productComponent.setField(BASIC_MODEL_PRODUCT, getProductByNumber(values.get(L_PRODUCT_NR)));

        productComponent = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, "operationProductOutComponent").save(
                productComponent);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test product component {product="
                    + ((Entity) productComponent.getField(BASIC_MODEL_PRODUCT)).getField(L_NUMBER)
                    + ", operation="
                    + ((Entity) ((Entity) productComponent.getField(L_OPERATION_COMPONENT)).getField(TECHNOLOGY_MODEL_OPERATION))
                            .getField(L_NUMBER) + ", quantity=" + productComponent.getField(L_QUANTITY) + "}");
        }
    }

    private void addMaterialRequirements(final Map<String, String> values) {
        Entity requirement = dataDefinitionService.get(SamplesConstants.MATERIALREQUIREMENTS_PLUGIN_IDENTIFIER,
                SamplesConstants.MATERIALREQUIREMENTS_MODEL_MATERIALREQUIREMENTS).create();
        requirement.setField(L_NAME, values.get(L_NAME));
        requirement.setField(L_NUMBER, values.get(L_NUMBER));
        requirement.setField(L_DATE, values.get(L_DATE));
        requirement.setField(L_WORKER, values.get(L_WORKER));
        requirement.setField("mrpAlgorithm", "01onlyComponents");
        requirement.setField(L_DATE, values.get(L_DATE));
        requirement.setField(L_GENERATED, values.get(L_GENERATED));
        requirement.setField(L_FILE_NAME, values.get(L_FILE_NAME));
        requirement.setField("orders",
                Lists.newArrayList(getOrderByNumber(values.get("order1")), getOrderByNumber(values.get("order2"))));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test material requirement {name=" + requirement.getField(L_NAME) + ", date="
                    + requirement.getField(L_DATE) + ", worker=" + requirement.getField(L_WORKER) + ", onlyComponents="
                    + requirement.getField("mrpAlgorithm") + ", generated=" + requirement.getField(L_GENERATED) + "}");
        }

        dataDefinitionService.get(SamplesConstants.MATERIALREQUIREMENTS_PLUGIN_IDENTIFIER,
                SamplesConstants.MATERIALREQUIREMENTS_MODEL_MATERIALREQUIREMENTS).save(requirement);
    }

    private void addWorkPlan(final Map<String, String> values) {

        Entity workPlan = dataDefinitionService.get(SamplesConstants.WORK_PLANS_PLUGIN_IDENTIFIER,
                SamplesConstants.WORK_PLANS_MODEL_WORK_PLAN).create();
        workPlan.setField(L_NAME, values.get(L_NAME));
        workPlan.setField(L_GENERATED, values.get(L_GENERATED));
        workPlan.setField(L_DATE, values.get(L_DATE));
        workPlan.setField(L_WORKER, values.get(L_WORKER));
        workPlan.setField(L_TYPE, values.get(L_TYPE));
        workPlan.setField(L_FILE_NAME, values.get("filename"));
        workPlan.setField("orders", Lists.newArrayList(getOrderByNumber(values.get(L_ORDER))));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add test material requirement {name=" + workPlan.getField(L_NAME) + ", date=" + workPlan.getField(L_DATE)
                    + ", worker=" + workPlan.getField(L_WORKER) + ", generated=" + workPlan.getField(L_GENERATED) + "}");
        }

        dataDefinitionService.get(SamplesConstants.WORK_PLANS_PLUGIN_IDENTIFIER, SamplesConstants.WORK_PLANS_MODEL_WORK_PLAN)
                .save(workPlan);
    }

    void addProductionRecord(final Map<String, String> values) {
        Entity productionRecord = dataDefinitionService.get(SamplesConstants.PRODUCTION_COUNTING_PLUGIN_IDENTIFIER,
                SamplesConstants.PRODUCTION_RECORD_MODEL_PRODUCTION_RECORD).create();

        productionRecord.setField(L_NUMBER, values.get(L_NUMBER));
        productionRecord.setField(L_ORDER, getOrderByNumber(values.get(L_ORDER)));
        productionRecord.setField(L_LAST_RECORD, values.get("lastrecord"));
        productionRecord.setField(L_MACHINE_TIME, values.get("machinetime"));
        productionRecord.setField(L_LABOR_TIME, values.get("labortime"));
        productionRecord.setField(L_STAFF, getStaffByNumber(values.get(L_STAFF)));
        productionRecord.setField(L_SHIFT, getShiftByName(values.get(L_SHIFT)));
        productionRecord.setField(L_WORKSTATION_TYPE, getWorkstationTypeByNumber(values.get("workstationtype")));
        productionRecord.setField(L_DIVISION, getDivisionByNumber(values.get(L_DIVISION)));
        productionRecord.setField(L_TECHNOLOGY_OPERATION_COMPONENT,
                getTechnologyOperationComponentByNumber(values.get(L_OPERATION), getOrderByNumber(values.get(L_ORDER))));

        String typeOfProductionRecording = productionRecord.getBelongsToField(L_ORDER)
                .getStringField("typeOfProductionRecording");
        Entity technologyOperationComponent = productionRecord.getBelongsToField(L_TECHNOLOGY_OPERATION_COMPONENT);

        if ((technologyOperationComponent != null) && !("03forEach".equals(typeOfProductionRecording))) {
            productionRecord.getDataDefinition().save(productionRecord);
        }
    }

    private void prepareProductionRecords(final Map<String, String> values) {
        Entity order = getOrderByNumber(values.get(L_ORDER));
        for (Entity productionRecord : order.getHasManyField("productionRecords")) {
            getStateChangeSamplesClient().changeState(productionRecord, STATE_ACCEPTED);
        }
    }

    void addProductionCounting(final Map<String, String> values) {
        Entity productionCounting = dataDefinitionService.get(SamplesConstants.PRODUCTION_COUNTING_PLUGIN_IDENTIFIER,
                SamplesConstants.PRODUCTION_COUNTING_MODEL_PRODUCTION_COUNTING).create();
        productionCounting.setField(L_GENERATED, values.get(L_GENERATED));
        productionCounting.setField(L_ORDER, getOrderByNumber(values.get(L_ORDER)));
        productionCounting.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT)));
        productionCounting.setField(L_NAME, values.get(L_NAME));
        productionCounting.setField(L_DATE, values.get(L_DATE));
        productionCounting.setField(L_WORKER, values.get(L_WORKER));
        productionCounting.setField(L_DESCRIPTION, values.get(L_DESCRIPTION));
        productionCounting.setField(L_FILE_NAME, values.get("filename"));

        dataDefinitionService.get(SamplesConstants.PRODUCTION_COUNTING_PLUGIN_IDENTIFIER,
                SamplesConstants.PRODUCTION_COUNTING_MODEL_PRODUCTION_COUNTING).save(productionCounting);
    }

    void addProductionBalance(final Map<String, String> values) {
        Entity productionbalance = dataDefinitionService.get(SamplesConstants.PRODUCTION_COUNTING_PLUGIN_IDENTIFIER,
                SamplesConstants.PRODUCTIONBALANCE_MODEL_PRODUCTIONBALANCE).create();
        productionbalance.setField(L_GENERATED, values.get(L_GENERATED));
        productionbalance.setField(L_ORDER, getOrderByNumber(values.get(L_ORDER)));
        productionbalance.setField(L_PRODUCT, getProductByNumber(values.get(L_PRODUCT)));
        productionbalance.setField(L_NAME, values.get(L_NAME));
        productionbalance.setField(L_DATE, values.get(L_DATE));
        productionbalance.setField(L_WORKER, values.get(L_WORKER));
        productionbalance.setField("recordsNumber", values.get("recordsnumber"));
        productionbalance.setField(L_DESCRIPTION, values.get(L_DESCRIPTION));
        productionbalance.setField(L_FILE_NAME, values.get("filename"));
        productionbalance.setField("calculateOperationCostsMode", values.get("calculateoperationcostsmode"));

        if (isEnabledOrEnabling("productionCountingWithCosts")) {
            productionbalance.setField("sourceOfMaterialCosts", values.get("sourceofmaterialcosts"));
            productionbalance.setField("calculateMaterialCostsMode", values.get("calculatematerialcostsmode"));

            productionbalance.setField("averageMachineHourlyCost", values.get("averagemachinehourlycost"));
            productionbalance.setField("averageLaborHourlyCost", values.get("averagelaborhourlycost"));
        }

        productionbalance.getDataDefinition().save(productionbalance);
    }

    void addQualityControl(final Map<String, String> values) {

        Entity qualitycontrol = dataDefinitionService.get(SamplesConstants.QUALITYCONTROL_PLUGIN_IDENTIFIER,
                SamplesConstants.QUALITYCONTROL_MODEL_QUALITYCONTROL).create();

        if ("qualityControlsForUnit".equals(values.get(L_QUALITYCONTROLTYPE_3))) {
            qualitycontrol.setField(L_NUMBER, values.get(L_NUMBER));
            qualitycontrol.setField(L_ORDER, getOrderByNumber(values.get(L_ORDER)));
            qualitycontrol.setField(L_COMMENT, values.get(L_COMMENT));
            qualitycontrol.setField(L_CLOSED, values.get(L_CLOSED));
            qualitycontrol.setField("controlledQuantity", values.get("controlledquantity"));
            qualitycontrol.setField("takenForControlQuantity", values.get("takenforcontrolquantity"));
            qualitycontrol.setField("rejectedQuantity", values.get("rejectedquantity"));
            qualitycontrol.setField("acceptedDefectsQuantity", values.get("accepteddefectsquantity"));
            qualitycontrol.setField(L_STAFF, values.get(L_STAFF));
            qualitycontrol.setField(L_DATE, values.get(L_DATE));
            qualitycontrol.setField("controlInstruction", values.get("controlinstruction"));
            qualitycontrol.setField(L_QUALITY_CONTROL_TYPE2, values.get(L_QUALITYCONTROLTYPE_3));

        } else if ("qualityControlsForOrder".equals(values.get(L_QUALITYCONTROLTYPE_3))) {
            qualitycontrol.setField(L_NUMBER, values.get(L_NUMBER));
            qualitycontrol.setField(L_ORDER, getOrderByNumber(values.get(L_ORDER)));
            qualitycontrol.setField("ControlResult", values.get("controlresult"));
            qualitycontrol.setField(L_COMMENT, values.get(L_COMMENT));
            qualitycontrol.setField(L_CLOSED, values.get(L_CLOSED));
            qualitycontrol.setField("controlInstruction", values.get("controlinstruction"));
            qualitycontrol.setField(L_STAFF, values.get(L_STAFF));
            qualitycontrol.setField(L_DATE, values.get(L_DATE));
            qualitycontrol.setField(L_QUALITY_CONTROL_TYPE2, values.get(L_QUALITYCONTROLTYPE_3));

        } else if (L_QUALITY_CONTROLS_FOR_OPERATION.equals(values.get(L_QUALITYCONTROLTYPE_3))) {
            qualitycontrol.setField(L_NUMBER, values.get(L_NUMBER));
            qualitycontrol.setField(L_ORDER, getOrderByNumber(values.get(L_ORDER)));
            qualitycontrol.setField(
                    L_OPERATION,
                    getTechnologyInstanceOperationComponentByNumber(values.get(L_OPERATION),
                            getOrderByNumber(values.get(L_ORDER))));
            qualitycontrol.setField("ControlResult", values.get("controlresult"));
            qualitycontrol.setField(L_COMMENT, values.get(L_COMMENT));
            qualitycontrol.setField(L_CLOSED, values.get(L_CLOSED));
            qualitycontrol.setField(L_STAFF, values.get(L_STAFF));
            qualitycontrol.setField(L_DATE, values.get(L_DATE));
            qualitycontrol.setField(L_QUALITY_CONTROL_TYPE2, values.get(L_QUALITYCONTROLTYPE_3));

        }

        qualitycontrol.getDataDefinition().save(qualitycontrol);
    }

    private void addWageGroups(final Map<String, String> values) {
        Entity wageGroups = dataDefinitionService.get(WAGE_GROUPS_PLUGIN_IDENTIFIER, WAGE_GROUPS_MODEL_IDENTIFIER).create();
        wageGroups.setField(L_NUMBER, values.get(L_NUMBER));
        wageGroups.setField(L_NAME, values.get(L_NAME));
        wageGroups.setField("superiorWageGroup", values.get("superior_wage_group"));
        wageGroups.setField(L_LABOR_HOURLY_COST, values.get("labor_hourly_cost"));

        Entity currency = dataDefinitionService
                .get(SamplesConstants.BASIC_PLUGIN_IDENTIFIER, SamplesConstants.BASIC_MODEL_CURRENCY).find()
                .add(SearchRestrictions.eq("alphabeticCode", values.get("code"))).uniqueResult();
        wageGroups.setField("laborHourlyCostCURRENCY", currency);
        wageGroups.getDataDefinition().save(wageGroups);

    }

    private Entity getRandomStaff() {
        Long total = (long) dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_STAFF).find().list()
                .getTotalNumberOfEntities();
        return dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_STAFF).find()
                .setFirstResult(RANDOM.nextInt(total.intValue())).setMaxResults(1).list().getEntities().get(0);
    }

    private Entity getMachine(final String id) {
        List<Entity> machines = dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_WORKSTATION_TYPE).find()
                .add(SearchRestrictions.eq(L_NUMBER, id)).list().getEntities();
        if (machines.isEmpty()) {
            return null;
        }
        return machines.get(0);
    }

    private Entity getTechnologyByNumber(final String number) {
        return dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, TECHNOLOGY_MODEL_TECHNOLOGY).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();
    }

    private Entity getDefaultTechnologyForProduct(final Entity product) {
        if (product == null) {
            return null;
        }
        List<Entity> technologies = dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, TECHNOLOGY_MODEL_TECHNOLOGY).find()
                .add(SearchRestrictions.belongsTo(BASIC_MODEL_PRODUCT, product)).add(SearchRestrictions.eq("master", true))
                .setMaxResults(1).list().getEntities();
        if (technologies.isEmpty()) {
            return null;
        } else {
            return technologies.get(0);
        }
    }

    private Entity getTechnologyOperationComponentByNumber(final String number, final Entity order) {
        Entity operation = dataDefinitionService
                .get(SamplesConstants.TECHNOLOGIES_PLUGIN_IDENTIFIER, SamplesConstants.TECHNOLOGY_MODEL_OPERATION).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();
        Entity technology = order.getBelongsToField(TECHNOLOGY_MODEL_TECHNOLOGY);

        return dataDefinitionService
                .get(SamplesConstants.TECHNOLOGIES_PLUGIN_IDENTIFIER,
                        SamplesConstants.TECHNOLOGY_MODEL_TECHNOLOGY_OPERATION_COMPONENT).find()
                .add(SearchRestrictions.belongsTo(L_TECHNOLOGY, technology))
                .add(SearchRestrictions.belongsTo(L_OPERATION, operation)).setMaxResults(1).uniqueResult();
    }

    private Entity getTechnologyInstanceOperationComponentByNumber(final String number, final Entity order) {
        Entity operation = dataDefinitionService
                .get(SamplesConstants.TECHNOLOGIES_PLUGIN_IDENTIFIER, SamplesConstants.TECHNOLOGY_MODEL_OPERATION).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();

        return dataDefinitionService
                .get(SamplesConstants.TECHNOLOGIES_PLUGIN_IDENTIFIER,
                        SamplesConstants.TECHNOLOGY_MODEL_TECHNOLOGY_INSTANCE_OPERATION_COMPONENT).find()
                .add(SearchRestrictions.belongsTo(L_ORDER, order)).add(SearchRestrictions.belongsTo(L_OPERATION, operation))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getShiftByName(final String name) {
        return dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, SamplesConstants.BASIC_MODEL_SHIFT).find()
                .add(SearchRestrictions.eq(L_NAME, name)).setMaxResults(1).uniqueResult();
    }

    private Entity getOrderByNumber(final String number) {
        return dataDefinitionService.get(ORDERS_PLUGIN_IDENTIFIER, ORDERS_MODEL_ORDER).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();
    }

    private Entity getProductByNumber(final String number) {
        return dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_PRODUCT).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();
    }

    private Entity getOperationByNumber(final String number) {
        return dataDefinitionService.get(TECHNOLOGIES_PLUGIN_IDENTIFIER, TECHNOLOGY_MODEL_OPERATION).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();
    }

    private Entity getStaffByNumber(final String number) {
        return dataDefinitionService.get("basic", L_STAFF).find().add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1)
                .uniqueResult();
    }

    private Entity getWorkstationTypeByNumber(final String number) {
        return dataDefinitionService.get("basic", "workstationType").find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getDivisionByNumber(final String number) {
        return dataDefinitionService.get("basic", L_DIVISION).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getTransformationByNumber(final String number) {
        return dataDefinitionService.get(L_MATERIAL_FLOW, L_TRANSFORMATIONS).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getLocationByNumber(final String number) {
        return dataDefinitionService.get(L_MATERIAL_FLOW, L_LOCATION).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getSupplierByNumber(final String number) {
        return dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, L_COMPANY).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getBatchByNumber(final String number) {
        return dataDefinitionService.get(L_ADVANCED_GENEALOGY, L_BATCH).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getWageGroup(final String number) {
        return dataDefinitionService.get(L_WAGE_GROUPS, L_WAGE_GROUP).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getRandomProduct() {
        Long total = (long) dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_PRODUCT).find().list()
                .getTotalNumberOfEntities();
        return dataDefinitionService.get(BASIC_PLUGIN_IDENTIFIER, BASIC_MODEL_PRODUCT).find()
                .setFirstResult(RANDOM.nextInt(total.intValue())).setMaxResults(1).list().getEntities().get(0);
    }

    private Entity getCompany(final String number) {
        return dataDefinitionService.get(SamplesConstants.BASIC_PLUGIN_IDENTIFIER, SamplesConstants.BASIC_MODEL_COMPANY).find()
                .add(SearchRestrictions.eq("number", number)).uniqueResult();
    }

    private DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    }

    private Entity getRequestForQuotationByNumber(final String number) {
        return dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, REQUEST_FOR_QUOTION).find()
                .add(SearchRestrictions.eq(L_NUMBER, number)).setMaxResults(1).uniqueResult();
    }

    private Entity getNegotationByNumber(final String number) {
        return dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, NEGOTIATION).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getOfferByNumber(final String number) {
        return dataDefinitionService.get(L_SUPPLY_NEGOTIATIONS, OFFER).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }

    private Entity getDeliveryByNumber(final String number) {
        return dataDefinitionService.get(L_DELIVERY, DELIVERY).find().add(SearchRestrictions.eq(L_NUMBER, number))
                .setMaxResults(1).uniqueResult();
    }
}
