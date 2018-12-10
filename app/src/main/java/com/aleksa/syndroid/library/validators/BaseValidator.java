package com.aleksa.syndroid.library.validators;

import com.aleksa.syndroid.library.router.request.Request;
import com.aleksa.syndroid.library.validators.exceptions.ValidationException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseValidator
{
    protected List<Rule> rules;

    public BaseValidator()
    {
        rules = new LinkedList<>();
    }

    public void validate(Request request) throws ValidationException
    {
        List<Rule> rules = filterRules(request);
        Map<String, String> params = request.getParams();
        Map<String, Map<String, String>> errors = new HashMap<>();

        beforeValidation(rules);

        validateRequired(params, rules, errors);
        validatePattern(params, rules, errors);

        afterValidation(errors, request);
    }

    private void beforeValidation(List<Rule> availableRules)
    {
        availableRules.add(new Rule("request_id", "*", "[0-9]+", true));

        for (Rule rule : availableRules) {
            rule.setPattern("^" + rule.getPattern() + "$");
        }
    }

    private void afterValidation(Map<String, Map<String,String>> errors, Request request) throws ValidationException
    {
        if (errors.keySet().size() > 0) {
            throw new ValidationException(errors, request);
        }
    }

    private void validateRequired(Map<String,String> params, List<Rule> rules, Map<String,Map<String, String>> errors)
    {
        for (Rule rule : rules) {
            if (rule.isRequired() && !(params.containsKey(rule.getName()))) {
                addValidationError(errors, rule.getName(), "required", "Param '" + rule.getName() + "' is required");
            }
        }
    }

    private void validatePattern(Map<String,String> params, List<Rule> rules, Map<String,Map<String,String>> errors)
    {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Rule rule = null;
            for (Rule item : rules) {
                if (item.getName().equals(entry.getKey())) {
                    rule = item;
                    break;
                }
            }

            if (rule == null) {
                continue;
            }

            if (!entry.getValue().matches(rule.getPattern())) {
                addValidationError(errors, entry.getKey(), "pattern", "Value '" + entry.getValue() + "' does not match required pattern");
            }
        }
    }

    private void addValidationError(Map<String, Map<String, String>> errors, String paramName, String errorName, String message)
    {
        if (errors.containsKey(paramName)) {
            errors.get(paramName).put(errorName, message);
            return;
        }

        Map<String, String> paramError = new HashMap<>();
        paramError.put(errorName, message);

        errors.put(paramName, paramError);
    }

    private List<Rule> filterRules(Request request)
    {
        List<Rule> filtered = new LinkedList<>();

        for (Rule rule : this.rules) {
            for (String handler : rule.getHandlers()) {
                if (rule.isGlobal() || handler.equals(request.getRoute().getHandler())) {
                    filtered.add(rule);
                }
            }
        }

        return filtered;
    }
}
