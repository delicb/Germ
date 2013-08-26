import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.Signature;

import germ.logging.*;
import germ.configuration.*;

public aspect GermTrace {

	private Logger logger = LogManager.getLogger();

	// logujemo sve sem onoga sto ima veze sa logovanjem (da program ne upadne u rekurziju)
	// i svih getera i setera ima previse tih poziva :)
	pointcut notToLog():
		 !execution(* *.set*(..)) && !execution(* *.get*()) && !within(GermTrace) && !execution(* *.is*()) && !within(LogManager) && 
	        !within(LogFormater) && !within(ConfigurationManager);
	

	pointcut traceMethods()
        : execution(* *.*(..))  && notToLog();
	
	pointcut traceConstructors()
		:execution(new(..)) && notToLog();

	before() : traceMethods() {

		Signature sig = thisJoinPointStaticPart.getSignature();

		logger.log(Level.INFO, getLogString(sig) + " [Entering]");
	}
	
	after() returning: traceMethods() {
		Signature sig = thisJoinPointStaticPart.getSignature();
		logger.log(Level.INFO, getLogString(sig) + " [Successful return]");
	}
	
	after() throwing: traceMethods() {
		Signature sig = thisJoinPointStaticPart.getSignature();
		logger.log(Level.WARNING, getLogString(sig) + " [Exception throwen]");
	}
	
	after() : traceConstructors() {
		Signature sig = thisJoinPointStaticPart.getSignature();
		logger.log(Level.INFO, getLogString(sig) + " [New object created]");
	}
	
	private String getLogString(Signature s) {
		return s.getDeclaringType().getName() + " -> " + s.getName();
	}

}