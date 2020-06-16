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
//POST and GET Practice
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    ArrayList<String> commentsShowing = new ArrayList<String>();
    int count;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the input from the form and add to ArrayList
        String newComments = getParameter(request, "text-input", "");
        //if there is a value add it to array
        if(newComments!=""){
            commentsShowing.add(newComments);
            count++;
        }

        /*if array already has a value, then reset comment with new value
        else if(count > 0){
            commentsShowing = new ArrayList<String>();
            if (newComments!=""){
                commentsShowing.add(newComments);
                count++;
            }
        }*/

        // Redirect back to the HTML page.
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("application/json");
          String json = convertToJson(commentsShowing, count);
          // Respond with the result.
          response.getWriter().println(json);
    }


  //convertToJson function
  private String convertToJson(ArrayList<String> commentsShowing, int size){
      String json = "{";
      if (size==1){
            json += "\"enteredComment\": ";
            json += "\"" + commentsShowing.get(0) + "\"";
            json += "}";
        }
        else if(size == 2){
            json += "\"enteredComment\": ";
            json += "\"" + commentsShowing.get(0) + "\"";
            json += ", "; 
            json += "\"enteredComment\": ";
            json += "\"" + commentsShowing.get(1) + "\"";
            json += "}";
        }
        else {
            for (int i =0; i<size-1; i++){
                json += "\"enteredComment\": ";
                json += "\"" + commentsShowing.get(i) + "\"";
                json += ", ";
            }
            json += "\"enteredComment\": ";
            json += "\"" + commentsShowing.get(size-1) + "\"";
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