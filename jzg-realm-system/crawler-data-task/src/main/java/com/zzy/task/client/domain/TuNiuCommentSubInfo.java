package com.zzy.task.client.domain;

public class TuNiuCommentSubInfo {

        private String score;  //": 4.7,
        private String scoreDesc;  //": "超棒！",
        private Integer count;  //": 107

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getScoreDesc() {
            return scoreDesc;
        }

        public void setScoreDesc(String scoreDesc) {
            this.scoreDesc = scoreDesc;
        }

}
