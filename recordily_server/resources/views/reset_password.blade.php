<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Laravel</title>

        <!-- Fonts -->
        <link href="https://fonts.bunny.net/css2?family=Nunito:wght@400;600;700&display=swap" rel="stylesheet">

        <!-- Styles -->
        <style>
            /*! normalize.css v8.0.1 | MIT License | github.com/necolas/normalize.css */html{line-height:1.15;-webkit-text-size-adjust:100%}body{margin:0}a{background-color:transparent}[hidden]{display:none}html{font-family:system-ui,-apple-system,BlinkMacSystemFont,Segoe UI,Roboto,Helvetica Neue,Arial,Noto Sans,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol,Noto Color Emoji;line-height:1.5}*,:after,:before{box-sizing:border-box;border:0 solid #e2e8f0}a{color:inherit;text-decoration:inherit}svg,video{display:block;vertical-align:middle}video{max-width:100%;height:auto}.bg-white{--bg-opacity:1;background-color:#fff;background-color:rgba(255,255,255,var(--bg-opacity))}.bg-gray-100{--bg-opacity:1;background-color:#f7fafc;background-color:rgba(247,250,252,var(--bg-opacity))}.border-gray-200{--border-opacity:1;border-color:#edf2f7;border-color:rgba(237,242,247,var(--border-opacity))}.border-t{border-top-width:1px}.flex{display:flex}.grid{display:grid}.hidden{display:none}.items-center{align-items:center}.justify-center{justify-content:center}.font-semibold{font-weight:600}.h-5{height:1.25rem}.h-8{height:2rem}.h-16{height:4rem}.text-sm{font-size:.875rem}.text-lg{font-size:1.125rem}.leading-7{line-height:1.75rem}.mx-auto{margin-left:auto;margin-right:auto}.ml-1{margin-left:.25rem}.mt-2{margin-top:.5rem}.mr-2{margin-right:.5rem}.ml-2{margin-left:.5rem}.mt-4{margin-top:1rem}.ml-4{margin-left:1rem}.mt-8{margin-top:2rem}.ml-12{margin-left:3rem}.-mt-px{margin-top:-1px}.max-w-6xl{max-width:72rem}.min-h-screen{min-height:100vh}.overflow-hidden{overflow:hidden}.p-6{padding:1.5rem}.py-4{padding-top:1rem;padding-bottom:1rem}.px-6{padding-left:1.5rem;padding-right:1.5rem}.pt-8{padding-top:2rem}.fixed{position:fixed}.relative{position:relative}.top-0{top:0}.right-0{right:0}.shadow{box-shadow:0 1px 3px 0 rgba(0,0,0,.1),0 1px 2px 0 rgba(0,0,0,.06)}.text-center{text-align:center}.text-gray-200{--text-opacity:1;color:#edf2f7;color:rgba(237,242,247,var(--text-opacity))}.text-gray-300{--text-opacity:1;color:#e2e8f0;color:rgba(226,232,240,var(--text-opacity))}.text-gray-400{--text-opacity:1;color:#cbd5e0;color:rgba(203,213,224,var(--text-opacity))}.text-gray-500{--text-opacity:1;color:#a0aec0;color:rgba(160,174,192,var(--text-opacity))}.text-gray-600{--text-opacity:1;color:#718096;color:rgba(113,128,150,var(--text-opacity))}.text-gray-700{--text-opacity:1;color:#4a5568;color:rgba(74,85,104,var(--text-opacity))}.text-gray-900{--text-opacity:1;color:#1a202c;color:rgba(26,32,44,var(--text-opacity))}.underline{text-decoration:underline}.antialiased{-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}.w-5{width:1.25rem}.w-8{width:2rem}.w-auto{width:auto}.grid-cols-1{grid-template-columns:repeat(1,minmax(0,1fr))}@media (min-width:640px){.sm\:rounded-lg{border-radius:.5rem}.sm\:block{display:block}.sm\:items-center{align-items:center}.sm\:justify-start{justify-content:flex-start}.sm\:justify-between{justify-content:space-between}.sm\:h-20{height:5rem}.sm\:ml-0{margin-left:0}.sm\:px-6{padding-left:1.5rem;padding-right:1.5rem}.sm\:pt-0{padding-top:0}.sm\:text-left{text-align:left}.sm\:text-right{text-align:right}}@media (min-width:768px){.md\:border-t-0{border-top-width:0}.md\:border-l{border-left-width:1px}.md\:grid-cols-2{grid-template-columns:repeat(2,minmax(0,1fr))}}@media (min-width:1024px){.lg\:px-8{padding-left:2rem;padding-right:2rem}}@media (prefers-color-scheme:dark){.dark\:bg-gray-800{--bg-opacity:1;background-color:#2d3748;background-color:rgba(45,55,72,var(--bg-opacity))}.dark\:bg-gray-900{--bg-opacity:1;background-color:#1a202c;background-color:rgba(26,32,44,var(--bg-opacity))}.dark\:border-gray-700{--border-opacity:1;border-color:#4a5568;border-color:rgba(74,85,104,var(--border-opacity))}.dark\:text-white{--text-opacity:1;color:#fff;color:rgba(255,255,255,var(--text-opacity))}.dark\:text-gray-400{--text-opacity:1;color:#cbd5e0;color:rgba(203,213,224,var(--text-opacity))}.dark\:text-gray-500{--tw-text-opacity:1;color:#6b7280;color:rgba(107,114,128,var(--tw-text-opacity))}}
        </style>

        <style>
            body {
                font-family: Verdana, Geneva, Tahoma, sans-serif;
            }

            .main_container{
                background-image: url("{{URL('/images/background.jpeg')}}");
                background-size: cover;
                display: flex;
                flex-direction: row;
                height: 100vh;
                width: 100vw;
                overflow-y:hidden
            }

            h2{
                font-size: 28px;
                color: #C01A56;
                text-shadow: 1px 1px 2px #F24583;
            }

            .container{
                width: 100%;
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .content{
                width: 25%;
                height: 60%;
                display: flex;
                flex-direction: column;
                align-items: center;
                border-radius: 20px;
                background-color: rgb(33, 33, 33);
                color: rgb(61, 61, 61);
                border: 1px solid #03989E;
                box-shadow: 2px 2px 8px #04b4bb;
            }

            img{
                margin-top: 5vh;
                height: 100px;
                width: 150px;
            }

            .textfield{
                position: relative;
                border-radius: 5px;
                margin: 1.5vh 0;
                width: 70%;
                height: 8%;
                outline: none;
            }

            .textfield input{
                width: 100%;
                padding: 0.5vh 0.5vw;
                height: 50px;
                font-size: 14px;
                background: none;
                outline: none;
                color: #FFFFFF;
                border: 1px solid #03989E;
                border-radius: 5px;
            }

            .textfield input:focus{
                border: 1px solid #03989E;
            }

            .textfield label{
                position: absolute;
                top: 30%;
                left: 5px;
                color: rgb(92, 92, 92);
                font-size: 14px;
                pointer-events: none;
            }

            .textfield input:focus ~ label,
            .textfield input:valid ~ label{
                padding-top:5px;
                top: -0.5px;
                color: #03989E;
                transition: 0.2s;
                font-size: 10px;
            }

            .textfield input:focus ~ span::before,
            .textfield input:valid ~ span::before{
                width: 100%;
            }

            input::placeholder{
                color: #979797;
                font-weight: 600;
            }

            .pink_btn{
                background-color: #C01A56;
                color:white;
                outline: none;
                border: none;
                border-radius: 20px;
                font-weight: bold;
                padding: 1.5vh 1vw;
                width: 40%;
                margin-top: 5vh;
                margin-bottom: 2vh;
                box-shadow: 1px 1px 4px #d75986;
            }

            .pink_btn:hover{
                background-color: #b0194f;
            }

            .error{
                color: #C01A56;
                font-size: 16px;
                font-weight: bold;
            }

            @media screen and (max-width: 800px) {
                .content{
                    width: 90%;
                    height:60%;
                    display: flex;
                }
            }
        </style>
    </head>

    <body>
    <div class="main_container">
        <div class="container">
            <div class="content">
                    <img src="{{URL('/images/logo.png')}}">

                    <h2>Reset Password</h2>
                    <div class="textfield">
                        <input type ="email" id="email" required>
                        <label>Email</label>
                    </div>

                    <div class="textfield">
                        <input type ="password" id="password" required>
                        <label>New Password</label>
                    </div>

                    <button id="submit" class="pink_btn">Reset Password</button>

                    <span class="error" id="error"></span>
            </div>
        </div>
    </div>

    <script>
        const email = document.getElementById("email");
        const password = document.getElementById("password");
        const error = document.getElementById("error");
        const loginButton = document.getElementById("submit");
        error.textContent = "";

        const login = () => {
            fetch("http://192.168.1.101/api/reset_password", {
                method: 'POST',
                body: new URLSearchParams({ "email": email.value,
                    "password": password.value}),
            })
                .then(response=>response.json()
                .then(res => error.textContent = "Password Reset"))
                .catch( er => error.textContent = "Invalid input" )
        }

        loginButton.addEventListener("click", login);
    </script>

    </body>
</html>
