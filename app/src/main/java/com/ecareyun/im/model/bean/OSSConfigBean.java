package com.ecareyun.im.model.bean;

public class OSSConfigBean {
    /**
     * code : 200
     * msg :
     * data : {"code":200,"expiration":1564732836000,"accessKeyId":"STS.NJHcpEaiSo1t4WqJVnpf5WGos","accessKeySecret":"H8tGhE3N65a1KWN42g6anK8fqwYJ3uDYVLk6SKnQZ79t","securityToken":"CAISiQJ1q6Ft5B2yfSjIr4n9KMrxjLZy2POfNnHArlY7fOkZuILEkTz2IH9Ff3JsBO8WsfkwmWxW7P4YlqVoRoReREvCKM1565kPCPgogSeE6aKP9rUhpMCPKwr6UmzGvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AFZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO7gEa4K+kkMqH8Uic3h+oiM1t/t2ve8D5Mpg0Zc0iAo3pgdYbLPSRjHRijDFR77pzgaB+/jPKg8qQGVE54W/dbLuPqYEyc18mO/dnR/Ae9KLm8+x/p/DSkontwhNXOuVYQ6EckExeVySuGoABeyTB4Zehjo6Ec7S/coqharsXJ5NHh6AHeLsIdhfFCivvxuncWvId48D0nK+oetWqCRQ+NeOmNlq1m0CtES18GcHW7HkggN8xnirpL7YeSPL2CWBNG8+J48EI3LFyZwf2R+OJpO79ivc8m7E9w8uEEm7DW/m6KsOq3DH0s5l655M=","requestId":"D5E405DD-5CC1-410C-8ABC-3248A0A4AA87"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OSSConfigBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * code : 200
         * expiration : 1564732836000
         * accessKeyId : STS.NJHcpEaiSo1t4WqJVnpf5WGos
         * accessKeySecret : H8tGhE3N65a1KWN42g6anK8fqwYJ3uDYVLk6SKnQZ79t
         * securityToken : CAISiQJ1q6Ft5B2yfSjIr4n9KMrxjLZy2POfNnHArlY7fOkZuILEkTz2IH9Ff3JsBO8WsfkwmWxW7P4YlqVoRoReREvCKM1565kPCPgogSeE6aKP9rUhpMCPKwr6UmzGvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AFZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO7gEa4K+kkMqH8Uic3h+oiM1t/t2ve8D5Mpg0Zc0iAo3pgdYbLPSRjHRijDFR77pzgaB+/jPKg8qQGVE54W/dbLuPqYEyc18mO/dnR/Ae9KLm8+x/p/DSkontwhNXOuVYQ6EckExeVySuGoABeyTB4Zehjo6Ec7S/coqharsXJ5NHh6AHeLsIdhfFCivvxuncWvId48D0nK+oetWqCRQ+NeOmNlq1m0CtES18GcHW7HkggN8xnirpL7YeSPL2CWBNG8+J48EI3LFyZwf2R+OJpO79ivc8m7E9w8uEEm7DW/m6KsOq3DH0s5l655M=
         * requestId : D5E405DD-5CC1-410C-8ABC-3248A0A4AA87
         */

        private int code;
        private long expiration;
        private String accessKeyId;
        private String accessKeySecret;
        private String securityToken;
        private String requestId;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "code=" + code +
                    ", expiration=" + expiration +
                    ", accessKeyId='" + accessKeyId + '\'' +
                    ", accessKeySecret='" + accessKeySecret + '\'' +
                    ", securityToken='" + securityToken + '\'' +
                    ", requestId='" + requestId + '\'' +
                    '}';
        }
    }
}
