package net.guerlab.spring.searchparams.tk.mapper.test;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author guer
 */
@Table(name = "test")
public class TestObj {

    @Id
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
