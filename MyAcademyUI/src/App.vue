<template>
    <header>
        <HeaderBar/>
    </header>
    <body>
        <MainHome/>
    </body>
    <!-- Other components or content -->
</template>

<script>
import HeaderBar from "@/components/HeaderBar.vue";
import MainHome from "@/components/MainHome.vue";

export default {
    components: {
        MainHome,
        HeaderBar
    }
};
</script>

<style scoped>
header {
    width: 100%;
    background-color: #f6f5f0;
    border-bottom: 1px solid #c8c8c8;
    /* position: relative; fixed가 있으니 필요가 없음 */
    position: fixed;
    top: 0;
    z-index: 9;
}
body {
    margin: 0 0;
    width: 100%;
    top: 120px;
    position: fixed;
    background-color: #2c2a29;
}
</style>