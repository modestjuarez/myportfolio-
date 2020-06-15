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
import java.util.ArrayList;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
//@WebServlet("/data")
/*public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //create and initialize array list
    ArrayList<String> commentsToShow = new ArrayList<String>();
    commentsToShow.add("Modest");
    commentsToShow.add("Phoenix");
    commentsToShow.add("Arizona");

    //Convert to Json
    String json = convertToJson(commentsToShow);

    //Send Json as response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  //convertToJson function
  private String convertToJson(ArrayList<String> commentsToShow){
    String json = "{";
    json += "\"name\": ";
    json += "\"" + commentsToShow.get(0) + "\"";
    json += ", ";
    json += "\"city\": ";
    json += "\"" + commentsToShow.get(1) + "\"";
    json += ", ";
    json += "\"state\": ";
    json += "\"" + commentsToShow.get(2) + "\"";
    json += "}";
    return json;
  }
}*/
//POST and GET Practice
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    ArrayList<String> commentsShowing = new ArrayList<String>();
    int count;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the input from the form and add to ArrayList
        String newComments = getParameter(request, "text-input", "");
        commentsShowing.add("Entered Comment: " + newComments);
        count++;

        // Redirect back to the HTML page.
        response.sendRedirect("/index.html");
        
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("application/json");
          commentsShowing.add("Testing");
          String json = convertToJson(commentsShowing, count);
          // Respond with the result.
          response.getWriter().println(json);
    }


  //convertToJson function
  private String convertToJson(ArrayList<String> commentsShowing, int size){
      String json = "{";
      if (size==0){
            json += "\"Comment\": ";
            json += "\"" + commentsShowing.get(0) + "\"";
            json += "}";
        }
        else if(size == 1){
            json += "\"Comment\": ";
            json += "\"" + commentsShowing.get(0) + "\"";
            json += ", "; 
            json += "\"Comment\": ";
            json += "\"" + commentsShowing.get(1) + "\"";
            json += "}";
        }
        else if(size > 1){
            for (int i =0; i<size-1; i++){
                json += "\"Comment\": ";
                json += "\"" + commentsShowing.get(i) + "\"";
                json += ", ";
            }
            json += "\"Comment\": ";
            json += "\"" + commentsShowing.get(size) + "\"";
            json += "}";
        }
        return json;
  }
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}