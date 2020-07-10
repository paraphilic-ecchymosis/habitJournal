// package com.crookedcoder.habitjournal.config;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.access.PermissionEvaluator;
// import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
// import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
// import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// import com.crookedcoder.habitjournal.entity.Journal;
// import com.crookedcoder.habitjournal.permissions.JournalPermissionEvaluator;

// import lombok.RequiredArgsConstructor;

// //@Configuration
// @RequiredArgsConstructor
// public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

//     // private final JournalPermissionEvaluator journalEvaluator;
	
// 	// @Override
// 	// protected MethodSecurityExpressionHandler createExpressionHandler() {
// 	// 	Map<String, PermissionEvaluator> evaluatorMap = new HashMap<>();
// 	// 	evaluatorMap.put(Journal.class.getName(), journalEvaluator);
// 	// 	PermissionEvaluator delegatingEvaluator = new DelegatingPermissionEvaluator(evaluatorMap);
// 	// 	DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
// 	// 	expressionHandler.setPermissionEvaluator(delegatingEvaluator);
// 	// 	return expressionHandler;
// 	// }
    
// }