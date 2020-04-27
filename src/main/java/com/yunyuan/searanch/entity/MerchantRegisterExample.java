package com.yunyuan.searanch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MerchantRegisterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MerchantRegisterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andRegistraIdIsNull() {
            addCriterion("registra_id is null");
            return (Criteria) this;
        }

        public Criteria andRegistraIdIsNotNull() {
            addCriterion("registra_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegistraIdEqualTo(Long value) {
            addCriterion("registra_id =", value, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdNotEqualTo(Long value) {
            addCriterion("registra_id <>", value, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdGreaterThan(Long value) {
            addCriterion("registra_id >", value, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdGreaterThanOrEqualTo(Long value) {
            addCriterion("registra_id >=", value, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdLessThan(Long value) {
            addCriterion("registra_id <", value, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdLessThanOrEqualTo(Long value) {
            addCriterion("registra_id <=", value, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdIn(List<Long> values) {
            addCriterion("registra_id in", values, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdNotIn(List<Long> values) {
            addCriterion("registra_id not in", values, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdBetween(Long value1, Long value2) {
            addCriterion("registra_id between", value1, value2, "registraId");
            return (Criteria) this;
        }

        public Criteria andRegistraIdNotBetween(Long value1, Long value2) {
            addCriterion("registra_id not between", value1, value2, "registraId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNull() {
            addCriterion("merchant_name is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNotNull() {
            addCriterion("merchant_name is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameEqualTo(String value) {
            addCriterion("merchant_name =", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotEqualTo(String value) {
            addCriterion("merchant_name <>", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThan(String value) {
            addCriterion("merchant_name >", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_name >=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThan(String value) {
            addCriterion("merchant_name <", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThanOrEqualTo(String value) {
            addCriterion("merchant_name <=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLike(String value) {
            addCriterion("merchant_name like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotLike(String value) {
            addCriterion("merchant_name not like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIn(List<String> values) {
            addCriterion("merchant_name in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotIn(List<String> values) {
            addCriterion("merchant_name not in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameBetween(String value1, String value2) {
            addCriterion("merchant_name between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotBetween(String value1, String value2) {
            addCriterion("merchant_name not between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneIsNull() {
            addCriterion("merchant_phone is null");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneIsNotNull() {
            addCriterion("merchant_phone is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneEqualTo(String value) {
            addCriterion("merchant_phone =", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotEqualTo(String value) {
            addCriterion("merchant_phone <>", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneGreaterThan(String value) {
            addCriterion("merchant_phone >", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_phone >=", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLessThan(String value) {
            addCriterion("merchant_phone <", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLessThanOrEqualTo(String value) {
            addCriterion("merchant_phone <=", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneLike(String value) {
            addCriterion("merchant_phone like", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotLike(String value) {
            addCriterion("merchant_phone not like", value, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneIn(List<String> values) {
            addCriterion("merchant_phone in", values, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotIn(List<String> values) {
            addCriterion("merchant_phone not in", values, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneBetween(String value1, String value2) {
            addCriterion("merchant_phone between", value1, value2, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andMerchantPhoneNotBetween(String value1, String value2) {
            addCriterion("merchant_phone not between", value1, value2, "merchantPhone");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNull() {
            addCriterion("id_card is null");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNotNull() {
            addCriterion("id_card is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardEqualTo(String value) {
            addCriterion("id_card =", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("id_card <>", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("id_card >", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("id_card >=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThan(String value) {
            addCriterion("id_card <", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("id_card <=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLike(String value) {
            addCriterion("id_card like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotLike(String value) {
            addCriterion("id_card not like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardIn(List<String> values) {
            addCriterion("id_card in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("id_card not in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("id_card between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("id_card not between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardImagIsNull() {
            addCriterion("id_card_imag is null");
            return (Criteria) this;
        }

        public Criteria andIdCardImagIsNotNull() {
            addCriterion("id_card_imag is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardImagEqualTo(String value) {
            addCriterion("id_card_imag =", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagNotEqualTo(String value) {
            addCriterion("id_card_imag <>", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagGreaterThan(String value) {
            addCriterion("id_card_imag >", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_imag >=", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagLessThan(String value) {
            addCriterion("id_card_imag <", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagLessThanOrEqualTo(String value) {
            addCriterion("id_card_imag <=", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagLike(String value) {
            addCriterion("id_card_imag like", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagNotLike(String value) {
            addCriterion("id_card_imag not like", value, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagIn(List<String> values) {
            addCriterion("id_card_imag in", values, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagNotIn(List<String> values) {
            addCriterion("id_card_imag not in", values, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagBetween(String value1, String value2) {
            addCriterion("id_card_imag between", value1, value2, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andIdCardImagNotBetween(String value1, String value2) {
            addCriterion("id_card_imag not between", value1, value2, "idCardImag");
            return (Criteria) this;
        }

        public Criteria andLicenseIsNull() {
            addCriterion("license is null");
            return (Criteria) this;
        }

        public Criteria andLicenseIsNotNull() {
            addCriterion("license is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseEqualTo(String value) {
            addCriterion("license =", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotEqualTo(String value) {
            addCriterion("license <>", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseGreaterThan(String value) {
            addCriterion("license >", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseGreaterThanOrEqualTo(String value) {
            addCriterion("license >=", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseLessThan(String value) {
            addCriterion("license <", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseLessThanOrEqualTo(String value) {
            addCriterion("license <=", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseLike(String value) {
            addCriterion("license like", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotLike(String value) {
            addCriterion("license not like", value, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseIn(List<String> values) {
            addCriterion("license in", values, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotIn(List<String> values) {
            addCriterion("license not in", values, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseBetween(String value1, String value2) {
            addCriterion("license between", value1, value2, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseNotBetween(String value1, String value2) {
            addCriterion("license not between", value1, value2, "license");
            return (Criteria) this;
        }

        public Criteria andLicenseImagIsNull() {
            addCriterion("license_imag is null");
            return (Criteria) this;
        }

        public Criteria andLicenseImagIsNotNull() {
            addCriterion("license_imag is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseImagEqualTo(String value) {
            addCriterion("license_imag =", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagNotEqualTo(String value) {
            addCriterion("license_imag <>", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagGreaterThan(String value) {
            addCriterion("license_imag >", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagGreaterThanOrEqualTo(String value) {
            addCriterion("license_imag >=", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagLessThan(String value) {
            addCriterion("license_imag <", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagLessThanOrEqualTo(String value) {
            addCriterion("license_imag <=", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagLike(String value) {
            addCriterion("license_imag like", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagNotLike(String value) {
            addCriterion("license_imag not like", value, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagIn(List<String> values) {
            addCriterion("license_imag in", values, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagNotIn(List<String> values) {
            addCriterion("license_imag not in", values, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagBetween(String value1, String value2) {
            addCriterion("license_imag between", value1, value2, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andLicenseImagNotBetween(String value1, String value2) {
            addCriterion("license_imag not between", value1, value2, "licenseImag");
            return (Criteria) this;
        }

        public Criteria andCheckIsNull() {
            addCriterion("check is null");
            return (Criteria) this;
        }

        public Criteria andCheckIsNotNull() {
            addCriterion("check is not null");
            return (Criteria) this;
        }

        public Criteria andCheckEqualTo(Boolean value) {
            addCriterion("check =", value, "check");
            return (Criteria) this;
        }

        public Criteria andCheckNotEqualTo(Boolean value) {
            addCriterion("check <>", value, "check");
            return (Criteria) this;
        }

        public Criteria andCheckGreaterThan(Boolean value) {
            addCriterion("check >", value, "check");
            return (Criteria) this;
        }

        public Criteria andCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("check >=", value, "check");
            return (Criteria) this;
        }

        public Criteria andCheckLessThan(Boolean value) {
            addCriterion("check <", value, "check");
            return (Criteria) this;
        }

        public Criteria andCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("check <=", value, "check");
            return (Criteria) this;
        }

        public Criteria andCheckIn(List<Boolean> values) {
            addCriterion("check in", values, "check");
            return (Criteria) this;
        }

        public Criteria andCheckNotIn(List<Boolean> values) {
            addCriterion("check not in", values, "check");
            return (Criteria) this;
        }

        public Criteria andCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("check between", value1, value2, "check");
            return (Criteria) this;
        }

        public Criteria andCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("check not between", value1, value2, "check");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeIsNull() {
            addCriterion("registra_time is null");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeIsNotNull() {
            addCriterion("registra_time is not null");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeEqualTo(Date value) {
            addCriterionForJDBCDate("registra_time =", value, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("registra_time <>", value, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("registra_time >", value, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("registra_time >=", value, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeLessThan(Date value) {
            addCriterionForJDBCDate("registra_time <", value, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("registra_time <=", value, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeIn(List<Date> values) {
            addCriterionForJDBCDate("registra_time in", values, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("registra_time not in", values, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("registra_time between", value1, value2, "registraTime");
            return (Criteria) this;
        }

        public Criteria andRegistraTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("registra_time not between", value1, value2, "registraTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}