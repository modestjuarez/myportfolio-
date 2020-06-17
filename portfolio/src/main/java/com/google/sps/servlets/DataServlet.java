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
        //if(newComments!=""){
            commentsShowing.add(newComments);
          //  count++;
        //}
        // Redirect back to the HTML page.
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("application/json");
        //String json = convertToJson(commentsShowing, count);
        String json = convertToJsonUsingGson(commentsShowing);
        // Respond with the result.
        response.getWriter().println(json);
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
