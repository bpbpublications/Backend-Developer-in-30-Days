import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding
public interface HelloService {
	@WebMethod
	String sayHello(String content);
}

@WebService(endpointInterface = "com.wstutorial.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	@Override
	public String sayHello(String name) {
		return "Hello " + name + " !";
	}

}


class Main {
  public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/ws/helloworld", new HelloWorldImpl());
		System.out.println("Service is running");
	}
}
