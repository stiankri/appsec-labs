module messaging {
  requires javafx.controls;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.dataformat.yaml;
  opens messaging.frontend to javafx.graphics;
  opens messaging.protocol to com.fasterxml.jackson.databind;
}
