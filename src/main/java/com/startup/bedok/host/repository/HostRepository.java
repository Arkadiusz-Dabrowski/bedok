package com.startup.bedok.host.repository;

import com.startup.bedok.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HostRepository extends JpaRepository<Host, UUID> {
}
