/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.session;

import static org.fest.assertions.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maxmind.geoip2.DatabaseReader;

import sample.config.GeoConfig;

/**
 * @author Rob Winch
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GeoConfig.class)
public class SessionDetailsFilterTests {
	@Autowired
	DatabaseReader reader;

	SessionDetailsFilter filter;

	@Before
	public void setup() {
		filter = new SessionDetailsFilter(reader);
	}

	@Test
	public void getGeoLocationHanldesInvalidIp() {
		assertThat(filter.getGeoLocation("a")).isEqualTo(SessionDetailsFilter.UNKNOWN);
	}

	@Test
	public void getGeoLocationNullCity() {
		assertThat(filter.getGeoLocation("22.231.113.64")).isEqualTo("United States");
	}

	@Test
	public void getGeoLocationBoth() {
		assertThat(filter.getGeoLocation("184.154.83.119")).isEqualTo("Chicago, United States");
	}
}