package fun.nya.backend.util.result;

public class Result<T> {
    private Boolean success;
    private String error;
    private T data;

    public Result() {
        this.success = true;
        this.error = null;
        this.data = null;
    }
    public Result(T data) {
        this.success = true;
        this.error = null;
        this.data = data;
    }
    public Result(String error) {
        this.success = false;
        this.error = error;
        this.data = null;
    }
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
