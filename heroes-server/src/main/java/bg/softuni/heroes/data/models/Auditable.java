package bg.softuni.heroes.data.models;

public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);
}
