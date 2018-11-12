import axios from './axios';

const ARTICLEPAGESERVLET = Symbol("ArticlePageServlet");
const ARTICLESERVLET =  Symbol("ArticleServlet");
const COMMENTCURRENT = Symbol("CommentCurrent");
const PIGEONHOLE = Symbol("pigeonhole");
const ADDCOMMENT = Symbol("AddComment");

const api_names = {
    [ARTICLEPAGESERVLET]: "http://localhost:222/BucaiBlog/ArticlePageServlet",
    // http://localhost:222/BucaiBlog/ArticleServlet?id=1113
    [ARTICLESERVLET]: "http://localhost:222/BucaiBlog/ArticleServlet",
    [COMMENTCURRENT]: "http://localhost:222/BucaiBlog/CommentCurrent",
    [PIGEONHOLE]: "http://localhost:222/BucaiBlog/Pigeonhole",
    [ADDCOMMENT]: "http://localhost:222/BucaiBlog/AddComment",
}

export const ArticlePageServlet = async (limit, offset) => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[ARTICLEPAGESERVLET]}?limit=${limit}&offset=${offset}`)
            .then((res) => {
                resolve([null, res]);
            })
            .catch((error) => {
                reject(error);
            })

    }).catch((error) => {
        return [error, null];
    });
}


export const ArticleServlet = async (id) => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[ARTICLESERVLET]}?id=${id}`)
            .then((res) => {
                resolve([null, res]);
            })
            .catch((error) => {
                reject(error);
            })

    }).catch((error) => {
        return [error, null];
    });
}


export const CommentCurrent = async (id) => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[COMMENTCURRENT]}?id=${id}`)
            .then((res) => {
                resolve([null, res]);
            })
            .catch((error) => {
                reject(error);
            })

    }).catch((error) => {
        return [error, null];
    });
}

export const Pigeonhole = async () => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[PIGEONHOLE]}`)
            .then((res) => {
                resolve([null, res]);
            })
            .catch((error) => {
                reject(error);
            })

    }).catch((error) => {
        return [error, null];
    });
}
export const AddComment = async (data) => {
    return new Promise((resolve, reject) => {
        axios.post(`${api_names[ADDCOMMENT]}`,data)
            .then((res) => {
                resolve([null, res]);
            })
            .catch((error) => {
                reject(error);
            })

    }).catch((error) => {
        return [error, null];
    });
}