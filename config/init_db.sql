CREATE TABLE resume (
    uuid      CHAR(36) NOT NULL PRIMARY KEY,
    full_name TEXT     NOT NULL
);

CREATE TABLE contact (
    id          SERIAL,
    resume_uuid char(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);