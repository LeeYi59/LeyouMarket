package com.ly.order.web;

import com.ly.order.pojo.Address;
import com.ly.order.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;
    @GetMapping
    public ResponseEntity<List<Address>> queryAddressList(){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.queryAddressList());
    }

    @GetMapping("{id}")
    public ResponseEntity<Address> queryAddressById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.queryAddressById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable("id") Long id){
        addressService.deleteAddressById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    @PutMapping
    public ResponseEntity<Void> updateAddressById(@RequestBody Address address){
        addressService.updateAddressById(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
