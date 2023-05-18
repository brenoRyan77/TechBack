package br.com.fujideia.iesp.tecback.dto;

public class EntityFieldErroDTO {

	private String field;
    private String message;

    public EntityFieldErroDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }//FieldError

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
