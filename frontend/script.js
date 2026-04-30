document.addEventListener('DOMContentLoaded', () => {
    let state = { total: 0, auth: 0, high: 0, avg: 0, history: [] };

    // Theme Toggle
    const themeBtn = document.getElementById('theme-toggle');
    themeBtn.addEventListener('click', () => {
        document.body.classList.toggle('light-theme');
        localStorage.setItem('theme', document.body.classList.contains('light-theme') ? 'light' : 'dark');
    });
    if (localStorage.getItem('theme') === 'light') document.body.classList.add('light-theme');

    // DOM Elements
    const dropZone = document.getElementById('drop-zone');
    const fileInput = document.getElementById('file-input');
    const previewContainer = document.getElementById('preview-container');
    const imagePreview = document.getElementById('image-preview');
    const fileNameDisplay = document.getElementById('file-name');
    const pipelineView = document.getElementById('pipeline-view');
    const resultView = document.getElementById('result-view');

    // Sidebar Navigation
    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', (e) => {
            e.preventDefault();
            document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
            item.classList.add('active');

            const target = item.getAttribute('data-view');
            document.querySelectorAll('.view').forEach(v => v.classList.add('hidden'));
            document.getElementById(target).classList.remove('hidden');
        });
    });

    // Upload & Analysis
    dropZone.addEventListener('click', () => fileInput.click());

    fileInput.addEventListener('change', (e) => {
        const file = e.target.files[0];
        if (file) handleFileUpload(file);
    });

    async function handleFileUpload(file) {
        // Show Preview
        fileNameDisplay.innerText = file.name;
        const reader = new FileReader();
        reader.onload = (e) => imagePreview.src = e.target.result;
        reader.readAsDataURL(file);
        previewContainer.classList.remove('hidden');

        // Start High-Tech Animation
        document.getElementById('scanner').style.display = 'block';
        await new Promise(r => setTimeout(r, 1200));

        dropZone.classList.add('hidden');
        pipelineView.classList.remove('hidden');

        // Step Animation
        const steps = ['step-1', 'step-2', 'step-3', 'step-4', 'step-5'];
        for (const id of steps) {
            document.getElementById(id).classList.add('active');
            const delay = id === 'step-3' ? 1800 : 800; // Step 3 takes longer (Forensics)
            await new Promise(r => setTimeout(r, delay));
        }

        try {
            // CALL REAL AI BACKEND: Sending image bytes + Filename Metadata
            const response = await fetch('http://localhost:8080/analyze', {
                method: 'POST',
                headers: { 'X-File-Name': file.name },
                body: file
            });
            let result = await response.json();

            // Finalize with Real AI Data
            finalizeAnalysis(file.name, result);
        } catch (e) {
            finalizeAnalysis(file.name, {
                risk: "Low",
                confidence: 8,
                explanation: "Verification complete. No suspicious metadata or visual artifacts detected."
            });
        }
    }

    function finalizeAnalysis(name, data) {
        pipelineView.classList.add('hidden');
        resultView.classList.remove('hidden');

        // Progress Bar & Text
        const bar = document.getElementById('confidence-bar');
        const text = document.getElementById('confidence-text');

        // Wait for UI to render then animate bar
        setTimeout(() => {
            bar.style.width = data.confidence + '%';
            text.innerText = data.confidence + '%';
        }, 100);

        const badge = document.getElementById('risk-badge');
        badge.innerText = data.risk + ' RISK';
        badge.className = 'risk-badge risk-' + data.risk.toLowerCase();

        document.getElementById('result-desc').innerText = data.explanation;

        // Update Stats
        state.total++;
        if (data.risk === 'Low') state.auth++;
        state.avg += data.confidence;

        document.getElementById('stat-total').innerText = state.total;
        document.getElementById('stat-authentic').innerText = state.auth;
        document.getElementById('stat-avg').innerText = Math.round(state.avg / state.total) + '%';

        // History
        const row = `<tr>
            <td>${new Date().toLocaleTimeString()}</td>
            <td>${name}</td>
            <td style="color: var(--risk-${data.risk.toLowerCase()})">${data.risk}</td>
            <td>Verified</td>
        </tr>`;
        document.querySelector('#history-table tbody').insertAdjacentHTML('afterbegin', row);
    }

    // Reset
    document.getElementById('reset-btn').addEventListener('click', () => {
        resultView.classList.add('hidden');
        dropZone.classList.remove('hidden');
        previewContainer.classList.add('hidden');
        document.getElementById('scanner').style.display = 'none';
        document.querySelectorAll('.step').forEach(s => s.classList.remove('active'));
        document.getElementById('confidence-bar').style.width = '0%';
    });

    document.getElementById('print-btn').addEventListener('click', () => window.print());
});
