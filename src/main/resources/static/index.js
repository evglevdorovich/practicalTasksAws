async function uploadFile() {
    let fileName = document.getElementById('fileName').value;
    let file = document.getElementById('file').files[0];

    let resp = await fetch("/putObject", {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: fileName
    });
    let url = await resp.text();

    let respForDownloading = await fetch(url, {
        method: "PUT",
        headers: {'Content-Type': 'image/jpeg'},
        body: file
    });
    window.location.href = 'http://localhost:8080'
}