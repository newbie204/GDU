/**
 * 
 */
/**
 * 
 */
module miniZalo {
	requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
	
	opens client to javafx.graphics, javafx.fxml;
}