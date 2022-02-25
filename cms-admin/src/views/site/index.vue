<template>
    <div>
        <platform-index
            v-if="this.$store.getters.userInfo.role!==undefined&&this.$store.getters.userInfo.role===0"></platform-index>
        <user-index v-else></user-index>
    </div>
</template>

<script>
import userIndex from "@/views/site/components/userIndex";
import platformIndex from "@/views/site/components/platformIndex";

export default {
    name: "index",
    components: {
        userIndex, platformIndex
    },
    data() {
        return {}
    },
    created() {

    }
}
</script>

<style scoped>

</style>
