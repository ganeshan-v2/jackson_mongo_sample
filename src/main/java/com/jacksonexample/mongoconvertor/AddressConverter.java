package com.jacksonexample.mongoconvertor;

import com.jacksonexample.model.Address;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class AddressConverter {

    // convert Address Object to MongoDB DBObject
    // take special note of converting id String to ObjectId
    public static DBObject toDBObject(Address address) {

        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("address", address.getAddress())
                .append("city", address.getCity())
                .append("zipcode", address.getZipcode())
                .append("state", address.getState());
        if (address.getId() != null)
            builder = builder.append("_id", new ObjectId(address.getId()));
        return builder.get();
    }

    // convert DBObject Object to Address
    // take special note of converting ObjectId to String
    public static Address toAddress(DBObject doc) {
        Address address = new Address();
        address.setAddress((String) doc.get("address"));
        address.setCity((String) doc.get("city"));
        address.setZipcode((Integer) doc.get("zipcode"));
        address.setState((String) doc.get("state"));
        ObjectId id = (ObjectId) doc.get("_id");
        address.setId(id.toString());
        return address;

    }

}