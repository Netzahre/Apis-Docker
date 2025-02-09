module org.example.clientegraficoapis {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires org.apache.commons.net;
    requires com.fasterxml.jackson.annotation;
    requires okhttp3;
    requires com.google.gson;

    opens org.example.clientegraficoapis.model;
    opens org.example.clientegraficoapis.service to javafx.fxml;
    opens org.example.clientegraficoapis.session to javafx.fxml;
    opens org.example.clientegraficoapis to javafx.fxml;
    exports org.example.clientegraficoapis;
    exports org.example.clientegraficoapis.controller;
    opens org.example.clientegraficoapis.controller to javafx.fxml;
}