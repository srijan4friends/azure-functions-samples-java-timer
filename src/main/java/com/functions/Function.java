/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueOutput;
import com.microsoft.azure.functions.annotation.TimerTrigger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
	/*
	 * @FunctionName("HttpExample") public HttpResponseMessage run(
	 * 
	 * @HttpTrigger( name = "req", methods = {HttpMethod.GET, HttpMethod.POST},
	 * authLevel = AuthorizationLevel.ANONYMOUS)
	 * HttpRequestMessage<Optional<String>> request, final ExecutionContext context)
	 * { context.getLogger().info("Java HTTP trigger processed a request.");
	 * 
	 * // Parse query parameter final String query =
	 * request.getQueryParameters().get("name"); final String name =
	 * request.getBody().orElse(query);
	 * 
	 * if (name == null) { return
	 * request.createResponseBuilder(HttpStatus.BAD_REQUEST).
	 * body("Please pass a name on the query string or in the request body").build()
	 * ; } else { return request.createResponseBuilder(HttpStatus.OK).body("Hello, "
	 * + name).build(); } }
	 */
    
    @FunctionName("Timer")
    @QueueOutput(name = "myQueueItem", queueName = "walkthrough", connection = "AzureWebJobsStorage")
    public String functionHandler(@TimerTrigger(name = "timerInfo", schedule = "*/30 * * * * *") String timerInfo, final ExecutionContext executionContext) {
        executionContext.getLogger().info("Timer trigger input: " + timerInfo);
        executionContext.getLogger().info("connection string: " + System.getenv("JDBC_SQL_CONN"));
        try {
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://az-practice-sqlserv1.database.windows.net:1433;"
					+ "database=practiceDb1;user=practice@az-practice-sqlserv1;password=srijan$123;"
					+ "encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
			ResultSet rs = conn
		            .createStatement().executeQuery("select * from employee");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "From timer: \"" + timerInfo + "\"";
    }
}
