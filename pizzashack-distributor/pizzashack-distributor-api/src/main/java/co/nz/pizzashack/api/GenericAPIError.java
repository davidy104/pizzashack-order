package co.nz.pizzashack.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GenericAPIError {
  private Response.Status respStatus;

  private String errorMessage;

  public Response.Status getRespStatus() {
    return respStatus;
  }

  public void setRespStatus(Response.Status respStatus) {
    this.respStatus = respStatus;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static Builder getBuilder(Status respStatus, String errorMessage) {
    return new Builder(respStatus, errorMessage);
  }

  public static class Builder {

    private GenericAPIError built;

    public Builder(Status respStatus, String errorMessage) {
      built = new GenericAPIError();
      built.respStatus = respStatus;
      built.errorMessage = errorMessage;
    }

    public GenericAPIError build() {
      return built;
    }
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
