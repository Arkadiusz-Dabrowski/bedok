package com.startup.bedok.host.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.startup.bedok.host.model.Host;

public interface HostRepository extends JpaRepository<Host, Long> {
}
