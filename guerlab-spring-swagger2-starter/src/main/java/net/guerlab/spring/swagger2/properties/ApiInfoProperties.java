package net.guerlab.spring.swagger2.properties;

import java.util.List;

import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;

/**
 * ApiInfoBuilder配置
 *
 * @author guer
 *
 */
public class ApiInfoProperties {

    private static final String DEFAULT_VERSION = "2.0";

    private String title;

    private String description;

    private String termsOfServiceUrl;

    private Contact contact;

    private String license;

    private String licenseUrl;

    private String version = DEFAULT_VERSION;

    private List<VendorExtension<?>> vendorExtensions;

    /**
     * 返回title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title
     *
     * @param title
     *            title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 返回description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description
     *
     * @param description
     *            description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 返回termsOfServiceUrl
     *
     * @return termsOfServiceUrl
     */
    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    /**
     * 设置termsOfServiceUrl
     *
     * @param termsOfServiceUrl
     *            termsOfServiceUrl
     */
    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    /**
     * 返回contact
     *
     * @return contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * 设置contact
     *
     * @param contact
     *            contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * 返回license
     *
     * @return license
     */
    public String getLicense() {
        return license;
    }

    /**
     * 设置license
     *
     * @param license
     *            license
     */
    public void setLicense(String license) {
        this.license = license;
    }

    /**
     * 返回licenseUrl
     *
     * @return licenseUrl
     */
    public String getLicenseUrl() {
        return licenseUrl;
    }

    /**
     * 设置licenseUrl
     *
     * @param licenseUrl
     *            licenseUrl
     */
    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    /**
     * 返回version
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置version
     *
     * @param version
     *            version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 返回vendorExtensions
     *
     * @return vendorExtensions
     */
    public List<VendorExtension<?>> getVendorExtensions() {
        return vendorExtensions;
    }

    /**
     * 设置vendorExtensions
     *
     * @param vendorExtensions
     *            vendorExtensions
     */
    public void setVendorExtensions(List<VendorExtension<?>> vendorExtensions) {
        this.vendorExtensions = vendorExtensions;
    }
}
