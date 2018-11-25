import axios from './axios';

import { ApiUrl } from '@/config/config.js'


const ARTICLEPAGE = Symbol("ArticlePage");
const ARTICLE = Symbol("Article");
const COMMENTCURRENT = Symbol("CommentCurrent");
const PIGEONHOLE = Symbol("pigeonhole");
const ADDCOMMENT = Symbol("AddComment");
const TAGS = Symbol("Tags");
const TAG = Symbol("Tag");
const LINKSRAND = Symbol("LinksRand");
const LINKS = Symbol("Links");
const ADMINARTICLE = Symbol("AdminArticle");
const LOGIN = Symbol("Login");

const api_names = {
    [ARTICLEPAGE]: ApiUrl + "/ArticlePage",
    // http://localhost:222/BucaiBlog/Article?id=1113
    [ARTICLE]: ApiUrl + "/Article",
    [COMMENTCURRENT]: ApiUrl + "/CommentCurrent",
    [PIGEONHOLE]: ApiUrl + "/Pigeonhole",
    [ADDCOMMENT]: ApiUrl + "/AddComment",
    [TAGS]: ApiUrl + "/Tags",
    [TAG]: ApiUrl + "/Tag",
    [LINKSRAND]: ApiUrl + "/LinksRand",
    [LINKS]: ApiUrl + "/Links",
    // LOGIN
    [ADMINARTICLE]: ApiUrl + "/AdminArticle",
    [LOGIN]: ApiUrl + "/Login",
    // http://localhost:222/BucaiBlog/Tag?id=11
    // http://localhost:222/BucaiBlog/LinksRand

}

export const ArticlePage = async (limit, offset) => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[ARTICLEPAGE]}?limit=${limit}&offset=${offset}`)
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


export const Article = async (id) => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[ARTICLE]}?id=${id}`)
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
        axios.post(`${api_names[ADDCOMMENT]}`, data)
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

export const AddArticle = async (data) => {
    return new Promise((resolve, reject) => {
        axios.post(`${api_names[ARTICLE]}`, data)
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
export const UpdateArticle = async (data) => {
    return new Promise((resolve, reject) => {
        axios.put(`${api_names[ARTICLE]}`, data)
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
export const DeleteArticle = async (id) => {
    return new Promise((resolve, reject) => {
        axios.delete(`${api_names[ARTICLE]}?id=${id}`)
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

export const Tags = async () => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[TAGS]}`)
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

export const Tag = async (id) => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[TAG]}?id=${id}`)
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

export const LinksRand = async () => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[LINKSRAND]}`)
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
export const Links = async () => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[LINKS]}`)
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

export const AddLink = async (data) => {
    return new Promise((resolve, reject) => {
        axios.post(`${api_names[LINKS]}`, data)
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
export const UpdateLink = async (data) => {
    return new Promise((resolve, reject) => {
        axios.put(`${api_names[LINKS]}`, data)
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
export const DeleteLink = async (id) => {
    return new Promise((resolve, reject) => {
        axios.delete(`${api_names[LINKS]}?id=${id}`)
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

export const AdminArticle = async () => {
    return new Promise((resolve, reject) => {
        axios.get(`${api_names[ADMINARTICLE]}`)
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

export const Login = async (data) => {
    return new Promise((resolve, reject) => {
        axios.post(`${api_names[LOGIN]}`, data)
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