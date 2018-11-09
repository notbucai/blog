import axios from './axios';

const ARTICLEPAGESERVLET = Symbol("ArticlePageServlet");

const api_names = {
    [ARTICLEPAGESERVLET]: "http://localhost:222/BucaiBlog/ArticlePageServlet",
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
