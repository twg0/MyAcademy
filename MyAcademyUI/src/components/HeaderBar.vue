<template>
        <div class="inner">
            <div class="sub-menu">
                <ul class="menu">
                    <li>
                        <a href="/signIn">Sign In</a>
                    </li>
                    <li>
                        <a href="/myInfo">My Info</a>
                    </li>
                </ul>
            </div>
            <ul class="main-menu">
                <li class="item">
                    <div class="item__name">
                        학원관리
                    </div>
                </li>
                <li class="item">
                    <div class="item__name">
                        학생관리
                    </div>
                </li>
                <li class="item">
                    <div class="item__name">
                        학생관리
                    </div>
                </li>
                <li class="item">
                    <div class="item__name">
                        학생관리
                    </div>
                </li>
            </ul>
        </div>
</template>

<script>
export default {
    name: "HeaderBar"
}
</script>

<style scoped>
.inner {
    width: 1100px;
    margin: 0 auto;
    position: relative;
    height: 120px;
}

ul {
    display: block;
    list-style-type: none;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
}

.sub-menu {
    position: absolute;
    top: 10px;
    right: 0;
    display: block;
    padding: 0;
    margin: 0;
}
.sub-menu ul.menu {
    display: flex;
    margin: 0;
    padding: 0;
    font-family: Arial, sans-serif;
}

.sub-menu ul.menu li {
    position: relative;
}

.sub-menu ul.menu li::before {
     content: "";
     width: 1px;
     height: 12px;
     background-color: #e5e5e5;
     /* 자동으로 block으로 변환 */
     position: absolute;
     top: 0;
     bottom: 0;
     margin: auto 0;
 }

.sub-menu ul.menu li:first-child::before {
    display: none;
}

.sub-menu ul.menu li a {
    display: block;
    font-size: 12px;
    padding: 11px 16px;
    color: #656565;
}

.sub-menu ul.menu li a:hover {
    color: #AAFFA3;
}

.sub-menu .search {
    height: 34px;
    position: relative;
}

.sub-menu .search input {
    width: 36px;
    height: 34px;
    padding: 4px 10px;
    border: 1px solid #ccc;
    box-sizing: border-box;
    border-radius: 5px;
    outline: none;
    background-color: #fff;
    color: #777;
    font-size: 12px;
    transition: width .4s;
}

.sub-menu .search input:focus {
    width: 190px;
    border-color: #669900;
}

.sub-menu .search .material-icons {
    height: 24px;
    position: absolute;
    top: 0;
    bottom: 0;
    right: 5px;
    margin: auto;
    transition: .4s
}

.sub-menu .search.focused .material-icons {
    opacity: 0;
}

.main-menu {
    display: flex;
    position: absolute;
    margin: 0;
    padding: 0;
    bottom: 0;
    right: 0;
    z-index: 1;
}

.main-menu .item {
}

.main-menu .item .item__name {
    padding: 10px 20px 34px 20px;
    font-family: Arial, sans-serif;
    font-size: 13px;
}

.main-menu .item:hover .item__name {
    background: #fff;
    color: #002266;
    font-weight: bold;
    border-radius: 6px 6px 0 0;
}

.main-menu .item .item__contents {
    width: 100%;
    position: fixed;
    left: 0;
    display: none;
}

.main-menu .item:hover .item__contents {
    display: block;
}

.main-menu .item .item__contents .contents__menu {
    background-color: #2c2a29;
}

.main-menu .item .item__contents .contents__menu>ul {
    display: flex;
    padding: 20px 0;

}

.main-menu .item .item__contents .contents__menu>ul>li {
    width: 220px;
}

.main-menu .item .item__contents .contents__menu>ul>li h4 {
    padding: 3px 0 12px 0;
    font-size: 14px;
    color: #fff;
}

.main-menu .item .item__contents .contents__menu>ul>li ul li {
    padding: 5px 0;
    font-size: 12px;
    color: #999;
    cursor: pointer;
}

.main-menu .item .item__contents .contents__menu>ul>li ul li:hover {
    color: #669900;
}

.main-menu .item .item__contents .contents__texture {
    padding: 26px 0;
    font-size: 12px;
    background-image: url("../images/main_menu_pattern.jpg")
}

.main-menu .item .item__contents .contents__texture h4 {
    color: #999;
    font-weight: 700;
}

.main-menu .item .item__contents .contents__texture p {
    color: #669900;
    margin: 4px 0 14px;
}

.badges {
    position: absolute;
    top: 132px;
    right: 12px;
}

.badges .badge {
    border-radius: 10px;
    overflow: hidden;
    margin-bottom: 12px;
    box-shadow: 4px 4px 10px rgba(0, 0, 0, .15);
    cursor: pointer;
}
</style>