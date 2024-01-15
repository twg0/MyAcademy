<template>
    <div class="apple-style-form">
        <div class="input-group">
            <label for="schoolName">학원이름</label>
            <input type="text" id="schoolName" v-model="schoolName" />
        </div>

        <div class="input-group">
            <label for="address">주소</label>
            <input type="text" id="address" v-model="address" />
        </div>

        <div class="input-group">
            <label for="phoneNumber">전화번호</label>
            <input type="text" id="phoneNumber" v-model="phoneNumber" />
        </div>

        <div class="input-group">
            <label for="studentCount">학생 수</label>
            <input type="number" id="studentCount" v-model="studentCount" />
        </div>

        <div class="input-group">
            <label for="userId">ID</label>
            <input type="text" id="userId" v-model="userId" />
        </div>

        <div class="input-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" v-model="password" />
        </div>

        <div class="button-group">
            <button @click="submitForm">Submit</button>
            <button @click="resetForm">Reset</button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'AcademyInputForm',
    data() {
        return {
            schoolName: '',
            address: '',
            phoneNumber: '',
            studentCount: 0,
            userId: '',
            password: ''
        };
    },
    methods: {
        submitForm() {
            // Logic to submit the form data
            console.log('Form submitted!');
        },
        resetForm() {
            // Logic to reset the form fields
            this.schoolName = '';
            this.address = '';
            this.phoneNumber = '';
            this.studentCount = 0;
            this.userId = '';
            this.password = '';
        }
    }
};
</script>

<style scoped>
.apple-style-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
    max-width: 90%; /* Adjusted to match the container */
    margin: 0 auto;
}

.input-group {
    display: flex;
    flex-direction: column;
}

.input-group label {
    font-weight: bold;
}

.input-group input {
    padding: 8px;
    font-size: 16px;
}

.button-group {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.button-group button {
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
}
</style>
