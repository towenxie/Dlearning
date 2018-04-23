package org.towen.deeplearning.data.model;

public class Message {

    private final static int OK = 200;
    private final static int ERROR = 500;
    private final static int NO_CONTENT = 204;
    private final static int BAD_REQUEST = 400;

    public static class MessageEntry {
        private int status;

        public MessageEntry(int status) {
            super();
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class SuccessMsg extends MessageEntry {

        public SuccessMsg(int status, Object object) {
            super(status);
            this.data = object;
        }

        private Object data;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    public static class ErrorMsg extends MessageEntry {
        public ErrorMsg(int status, String message) {
            super(status);
            this.message = message;
        }

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class NoContentMsg extends MessageEntry {
        public NoContentMsg(int status, String message) {
            super(status);
            this.message = message;
        }

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class BadRequestMsg extends MessageEntry {
        public BadRequestMsg(int status, String message) {
            super(status);
            this.message = message;
        }

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static MessageEntry ok(Object result) {
        return new SuccessMsg(OK, result);
    }

    public static MessageEntry error(String message) {
        return new ErrorMsg(ERROR, message);
    }

    public static MessageEntry noContent(String message) {
        return new NoContentMsg(NO_CONTENT, message);
    }

    public static MessageEntry badRequest(String message) {
        return new BadRequestMsg(BAD_REQUEST, message);
    }
}