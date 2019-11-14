package com.dc.boot.ignite.config;

import java.util.Arrays;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.IgniteSpring;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.AtomicConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.springdata20.repository.config.EnableIgniteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dc.boot.ignite.annotation.Loggable;

@Configuration
@EnableIgniteRepositories
public class IgniteConfig {
	
	@Value("${app.ignite.instance.name}")
	private String igniteInstanceName;

	
	@Loggable
	public IgniteConfiguration igniteConfiguration() {
		IgniteConfiguration igniteConfig = new IgniteConfiguration();

		TcpDiscoverySpi discovery = new TcpDiscoverySpi();
		TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
		ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47510"));
		discovery.setIpFinder(ipFinder);

		AtomicConfiguration atomicCfg = new AtomicConfiguration();
		atomicCfg.setCacheMode(CacheMode.LOCAL);

		DataStorageConfiguration dataStorageCfg = new DataStorageConfiguration();
		DataRegionConfiguration dataRegionCfg = new DataRegionConfiguration();
		dataRegionCfg.setPersistenceEnabled(true);
		dataStorageCfg.setDefaultDataRegionConfiguration(dataRegionCfg);

		igniteConfig.setDiscoverySpi(discovery);
		igniteConfig.setAtomicConfiguration(atomicCfg);
		igniteConfig.setDataStorageConfiguration(dataStorageCfg);
		igniteConfig.setPeerClassLoadingEnabled(false);
		igniteConfig.setClientMode(true);
		igniteConfig.setIgniteInstanceName(igniteInstanceName);

		return igniteConfig;
	}
	
	
	@Bean
	@Autowired
	public Ignite igniteInstance(ApplicationContext context) throws IgniteCheckedException {
		return IgniteSpring.start(igniteConfiguration(),context);
	}
}
