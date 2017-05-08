package com.jacksonexample.dao;

import com.jacksonexample.model.Address;
import com.jacksonexample.mongoconvertor.AddressConverter;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Created by ganeshan on 6/5/17.
 */
public class AddressDAO {

    private DBCollection col;

    public AddressDAO(MongoClient mongo) {
        this.col = mongo.getDB("mangodbexample").getCollection("Addresses");
    }

    public Address createAddress(Address address) {
        DBObject doc = AddressConverter.toDBObject(address);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        address.setId(id.toString());
        return address;
    }

    public void updateAddress(Address address) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(address.getId())).get();
        this.col.update(query, AddressConverter.toDBObject(address));
    }

    public List<Address> readAllAddress() {
        List<Address> data = new ArrayList<Address>();
        DBCursor cursor = col.find();
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();
            Address address = AddressConverter.toAddress(doc);
            data.add(address);
        }
        return data;
    }

    public void deleteAddress(Address address) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(address.getId())).get();
        this.col.remove(query);
    }

    public Address readAddress(Address address) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(address.getId())).get();
        DBObject data = this.col.findOne(query);
        return AddressConverter.toAddress(data);
    }
}
