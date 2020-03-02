package net.guerlab.spring.searchparams.tk.mapper.test;

import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import java.util.Collection;

/**
 * @author guer
 */
public class TestSearchParams extends AbstractSearchParams {

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "k1 = ? and k2 = ?")
    private String singleValue;

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "k1 = ? and k2 = ?")
    private Collection<String> multiValue;

    @SearchModel(value = SearchModelType.CUSTOM_SQL, sql = "k1 = ? and k2 = ? and k3 = ? and k4 = ?")
    private Collection<String> multiValue2;

    public String getSingleValue() {
        return singleValue;
    }

    public void setSingleValue(String singleValue) {
        this.singleValue = singleValue;
    }

    public Collection<String> getMultiValue() {
        return multiValue;
    }

    public void setMultiValue(Collection<String> multiValue) {
        this.multiValue = multiValue;
    }

    public Collection<String> getMultiValue2() {
        return multiValue2;
    }

    public void setMultiValue2(Collection<String> multiValue2) {
        this.multiValue2 = multiValue2;
    }
}
