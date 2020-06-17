// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Task;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
//POST and GET Practice
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    ArrayList<String> commentsShowing = new ArrayList<String>();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the input from the form and add to ArrayList
        String commentValue = getParameter(request, "text-input", "");
            commentsShowing.add(commentValue);
        long timestamp = System.currentTimeMillis();

        //create an entity to store data on datastore
        Entity commentsDataEntity = new Entity("Task");
        //create properties for the entity created
        commentsDataEntity.setProperty("commentValue", commentValue);
        commentsDataEntity.setProperty("timestamp", timestamp);

        //Create an instance of DatastoreService Class then put entity in datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentsDataEntity);

        // Redirect back to the HTML page.
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        //Create query instance and sort by time
        Query query = new Query("Task").addSort("timestamp", SortDirection.ASCENDING);
        
        //Create an instance of DatastoreService class to use the prepare() function to retrieve comments
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //pass query to to the datastore.prepare() go get a PreparedQuery instance with all the comments in commentValue
        PreparedQuery allCommentsStored = datastore.prepare(query);
        //Loop through all the comments returned from datastore and store them in the ArrayList
        List<Task> commentsLoaded= new ArrayList<>();
            for (Entity entity : allCommentsStored.asIterable()) {
                long id = entity.getKey().getId();
                //retrieve data
                String commentValue = (String) entity.getProperty("commentValue");
                long timestamp = (long) entity.getProperty("timestamp");

                Task task = new Task(id, commentValue, timestamp);
                commentsLoaded.add(task);
            }


        Gson gson = new Gson();

        // Respond with the result.
        response.setContentType("application/json");
        //String json = convertToJsonUsingGson(commentsShowing);
        response.getWriter().println(gson.toJson(commentsLoaded));
    }

  private String convertToJsonUsingGson(ArrayList<String> commentsShowing) {
        Gson gson = new Gson();
        String json = gson.toJson(commentsShowing);
        return json;
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
          return defaultValue;
         }
        return value;
  }
}//end of java file
