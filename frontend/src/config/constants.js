/**
 * 아래의 상수들을 임의로 변경하여 구현할 수 있습니다. 
 */
export const CATEGORIES = {
    DIGITAL: "D",
    CHILD: "C",
    HOBBY: "H"
};

export const ITEM_STATUS = {
    ADDED: {
        symbol: "A",
        explanation: "등록됨"
    },
    ONSALE: {
        symbol: "S",
        explanation: "판매중"
    },
    DELIVER: {
        symbol: "D",
        explanation: "배송중"
    },
    CONFIRMED: {
        symbol: "C",
        explanation: "판매완료(구매확정)"
    },
    CANCELED: {
        symbol: "X",
        explanation: "취소됨"
    },
    DELETED: {
        symbol: "N",
        explanation: "삭제됨"
    },
    symbolToStatus(symbol) {
        switch (symbol) {
            case "A":
                return this.ADDED;
            case "S":
                return this.ONSALE;
            case "D":
                return this.DELIVER;
            case "C":
                return this.CONFIRMED;
            case "X":
                return this.CANCELED;
            case "N":
                return this.DELETED;
        }
    }
};

export const ESCROW_STATE = {
    INITIAL: {
        symbol: "I",
        explanation: "입금대기중"
    },
    PAID: {
        symbol: "P",
        explanation: "입금완료"
    },
    SENT: {
        symbol: "S",
        explanation: "배송완료"
    },
    CONFIRMED: {
        symbol: "C",
        explanation: "구매확정"
    },
    CANCELED: {
        symbol: "X",
        explanation: "거래취소"
    },
    
    symbolToState(symbol) {
        switch (symbol) {
            case "I":
                return this.INITIAL;
            case "P":
                return this.PAID;
            case "S":
                return this.SENT;
            case "C":
                return this.CONFIRMED;
            case "X":
                return this.CANCELED;
        }
    }
};