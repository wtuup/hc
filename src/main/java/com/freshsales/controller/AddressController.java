package com.freshsales.controller;

import com.freshsales.entity.*;
import com.freshsales.service.*;
import com.freshsales.util.Result;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public Result<List<Address>> list(@AuthenticationPrincipal UserDetails ud) {
        return Result.ok(addressService.getUserAddresses(userService.findByUsername(ud.getUsername()).getId()));
    }

    @PostMapping
    public Result<Void> save(@AuthenticationPrincipal UserDetails ud, @RequestBody Address address) {
        addressService.saveAddress(address, userService.findByUsername(ud.getUsername()).getId());
        return Result.ok();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@AuthenticationPrincipal UserDetails ud, @PathVariable Long id, @RequestBody Address address) {
        address.setId(id);
        addressService.saveAddress(address, userService.findByUsername(ud.getUsername()).getId());
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@AuthenticationPrincipal UserDetails ud, @PathVariable Long id) {
        addressService.deleteAddress(id, userService.findByUsername(ud.getUsername()).getId());
        return Result.ok();
    }

    @PutMapping("/{id}/default")
    public Result<Void> setDefault(@AuthenticationPrincipal UserDetails ud, @PathVariable Long id) {
        addressService.setDefault(id, userService.findByUsername(ud.getUsername()).getId());
        return Result.ok();
    }
}