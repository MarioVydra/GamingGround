package com.SemestralnaPraca.GamingGround.repository;

import com.SemestralnaPraca.GamingGround.entity.Address;
import com.SemestralnaPraca.GamingGround.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID>{
    Address findAddressByUser(User user);
}
