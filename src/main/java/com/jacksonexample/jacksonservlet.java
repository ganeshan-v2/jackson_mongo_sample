package com.jacksonexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacksonexample.dao.AddressDAO;
import com.jacksonexample.model.Address;
import com.jacksonexample.model.User;
import com.mongodb.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ganeshan on 6/5/17.
 */
public class jacksonservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // This will store all received Users
    List<User> users = new LinkedList<User>();

    /***************************************************
     * URL: /jsonservlet
     * doPost(): receives JSON data, parse it, map it and send back as JSON
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }

        // 2. initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();

        // 3. Convert received JSON to User
        User user = mapper.readValue(json, User.class);

        // 4. Set response type to JSON
        response.setContentType("application/json");

        // 5. Add user to List<User>
        users.add(user);

        // 6. Send List<User> as JSON to client

        Address address = new Address();
        address.setAddress("Virupakshapura");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setZipcode(560097);
        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        AddressDAO addressDAO = new AddressDAO(mongo);
        addressDAO.createAddress(address);
        System.out.println("Address Added Successfully");


        List<Address> addresses = addressDAO.readAllAddress();

        System.out.println("Addresses fetched "+addresses.get(0).getAddress());
        System.out.println("Addresses Count "+addresses.size());

        mapper.writeValue(response.getOutputStream(), users);
    }

}
