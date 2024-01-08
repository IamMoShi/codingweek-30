module eu.telecomnancy.condinglate {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens eu.telecomnancy.condinglate to javafx.fxml;
    exports eu.telecomnancy.condinglate;
    exports eu.telecomnancy.condinglate.UI;
    opens eu.telecomnancy.condinglate.UI to javafx.fxml;
}