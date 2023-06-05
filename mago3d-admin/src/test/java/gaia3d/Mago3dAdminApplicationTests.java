package gaia3d;

import java.util.EnumMap;
import java.util.Map;

import gaia3d.domain.DataInfo;
import gaia3d.persistence.DataMapper;
import gaia3d.security.Crypt;
import gaia3d.service.DataService;
import org.junit.jupiter.api.Test;

import gaia3d.domain.MenuTarget;
import gaia3d.domain.ShapeFileExt;
import org.springframework.beans.factory.annotation.Autowired;

class Mago3dAdminApplicationTests {
	@Autowired
	private DataMapper dataMapper;
	@Autowired
	private DataService dataService;


	@Test
	void contextLoads() {
		
		 System.out.println(ShapeFileExt.findBy("shp"));
	}
	@Test
	public void test(){
		System.out.println("你好");
		System.out.println(Crypt.decrypt("K+GYmkcLWitEt6IUOhEGx9A2eGNLFAThAYFISzUZoiPVKP8/7Vl11ayAEguhgWmf"));
	}
	@Test
	public void mapperTest(){
		DataInfo dataInfo =new DataInfo();
		System.out.println("mapper输出值："+dataMapper.getListData(dataInfo));
		System.out.println("service输出值："+dataService.getListData(dataInfo));
	}

}
