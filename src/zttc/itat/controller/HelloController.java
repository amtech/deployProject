package zttc.itat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 闁俺绻僣ontroller濞夈劏袙濞夈劌鍙嗛惃鍒ntroller
 * @author John
 *
 */
@Controller
public class HelloController{
	//requestMapping鐞涖劎銇氶悽銊ユ憿娑撶尳rl閺夈儱顕惔锟�
	@RequestMapping({"/hello.do","/"})
	public String hello(){
		return "hello"; 
	}
	
}
