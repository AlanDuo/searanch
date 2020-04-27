package com.yunyuan.searanch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PushExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PushExample() {
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

        public Criteria andPushIdIsNull() {
            addCriterion("push_id is null");
            return (Criteria) this;
        }

        public Criteria andPushIdIsNotNull() {
            addCriterion("push_id is not null");
            return (Criteria) this;
        }

        public Criteria andPushIdEqualTo(Long value) {
            addCriterion("push_id =", value, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdNotEqualTo(Long value) {
            addCriterion("push_id <>", value, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdGreaterThan(Long value) {
            addCriterion("push_id >", value, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdGreaterThanOrEqualTo(Long value) {
            addCriterion("push_id >=", value, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdLessThan(Long value) {
            addCriterion("push_id <", value, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdLessThanOrEqualTo(Long value) {
            addCriterion("push_id <=", value, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdIn(List<Long> values) {
            addCriterion("push_id in", values, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdNotIn(List<Long> values) {
            addCriterion("push_id not in", values, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdBetween(Long value1, Long value2) {
            addCriterion("push_id between", value1, value2, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushIdNotBetween(Long value1, Long value2) {
            addCriterion("push_id not between", value1, value2, "pushId");
            return (Criteria) this;
        }

        public Criteria andPushFromIsNull() {
            addCriterion("push_from is null");
            return (Criteria) this;
        }

        public Criteria andPushFromIsNotNull() {
            addCriterion("push_from is not null");
            return (Criteria) this;
        }

        public Criteria andPushFromEqualTo(Long value) {
            addCriterion("push_from =", value, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromNotEqualTo(Long value) {
            addCriterion("push_from <>", value, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromGreaterThan(Long value) {
            addCriterion("push_from >", value, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromGreaterThanOrEqualTo(Long value) {
            addCriterion("push_from >=", value, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromLessThan(Long value) {
            addCriterion("push_from <", value, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromLessThanOrEqualTo(Long value) {
            addCriterion("push_from <=", value, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromIn(List<Long> values) {
            addCriterion("push_from in", values, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromNotIn(List<Long> values) {
            addCriterion("push_from not in", values, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromBetween(Long value1, Long value2) {
            addCriterion("push_from between", value1, value2, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushFromNotBetween(Long value1, Long value2) {
            addCriterion("push_from not between", value1, value2, "pushFrom");
            return (Criteria) this;
        }

        public Criteria andPushToIsNull() {
            addCriterion("push_to is null");
            return (Criteria) this;
        }

        public Criteria andPushToIsNotNull() {
            addCriterion("push_to is not null");
            return (Criteria) this;
        }

        public Criteria andPushToEqualTo(Long value) {
            addCriterion("push_to =", value, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToNotEqualTo(Long value) {
            addCriterion("push_to <>", value, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToGreaterThan(Long value) {
            addCriterion("push_to >", value, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToGreaterThanOrEqualTo(Long value) {
            addCriterion("push_to >=", value, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToLessThan(Long value) {
            addCriterion("push_to <", value, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToLessThanOrEqualTo(Long value) {
            addCriterion("push_to <=", value, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToIn(List<Long> values) {
            addCriterion("push_to in", values, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToNotIn(List<Long> values) {
            addCriterion("push_to not in", values, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToBetween(Long value1, Long value2) {
            addCriterion("push_to between", value1, value2, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushToNotBetween(Long value1, Long value2) {
            addCriterion("push_to not between", value1, value2, "pushTo");
            return (Criteria) this;
        }

        public Criteria andPushContentIsNull() {
            addCriterion("push_content is null");
            return (Criteria) this;
        }

        public Criteria andPushContentIsNotNull() {
            addCriterion("push_content is not null");
            return (Criteria) this;
        }

        public Criteria andPushContentEqualTo(String value) {
            addCriterion("push_content =", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentNotEqualTo(String value) {
            addCriterion("push_content <>", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentGreaterThan(String value) {
            addCriterion("push_content >", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentGreaterThanOrEqualTo(String value) {
            addCriterion("push_content >=", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentLessThan(String value) {
            addCriterion("push_content <", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentLessThanOrEqualTo(String value) {
            addCriterion("push_content <=", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentLike(String value) {
            addCriterion("push_content like", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentNotLike(String value) {
            addCriterion("push_content not like", value, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentIn(List<String> values) {
            addCriterion("push_content in", values, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentNotIn(List<String> values) {
            addCriterion("push_content not in", values, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentBetween(String value1, String value2) {
            addCriterion("push_content between", value1, value2, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushContentNotBetween(String value1, String value2) {
            addCriterion("push_content not between", value1, value2, "pushContent");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNull() {
            addCriterion("push_time is null");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNotNull() {
            addCriterion("push_time is not null");
            return (Criteria) this;
        }

        public Criteria andPushTimeEqualTo(Date value) {
            addCriterion("push_time =", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotEqualTo(Date value) {
            addCriterion("push_time <>", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThan(Date value) {
            addCriterion("push_time >", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("push_time >=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThan(Date value) {
            addCriterion("push_time <", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThanOrEqualTo(Date value) {
            addCriterion("push_time <=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeIn(List<Date> values) {
            addCriterion("push_time in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotIn(List<Date> values) {
            addCriterion("push_time not in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeBetween(Date value1, Date value2) {
            addCriterion("push_time between", value1, value2, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotBetween(Date value1, Date value2) {
            addCriterion("push_time not between", value1, value2, "pushTime");
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