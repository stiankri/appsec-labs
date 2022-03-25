module schappsec.xray {
  requires javafx.graphics;
  requires org.antlr.antlr4.runtime;
  requires javafx.controls;
  opens com.schibsted.security.labs.analysis to javafx.graphics;
}
