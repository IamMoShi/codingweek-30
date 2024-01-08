module eu.telecomnancy.codinglate {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens eu.telecomnancy.codinglate to javafx.fxml;
    exports eu.telecomnancy.codinglate;
    exports eu.telecomnancy.codinglate.UI;
    opens eu.telecomnancy.codinglate.UI to javafx.fxml;
}