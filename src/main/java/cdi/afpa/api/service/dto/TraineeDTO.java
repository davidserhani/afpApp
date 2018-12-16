package cdi.afpa.api.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Trainee entity.
 */
public class TraineeDTO implements Serializable {

    private Long id;

    private String lastName;

    private String firstName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TraineeDTO traineeDTO = (TraineeDTO) o;
        if (traineeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), traineeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TraineeDTO{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            "}";
    }
}
