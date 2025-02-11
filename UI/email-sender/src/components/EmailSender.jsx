// eslint-disable-next-line no-unused-vars
import React from "react";

function EmailSender() {
  return (
    <div className="w-full bg-amber-500 min-h-screen flex justify-center items-center">
      <div className="email_card bg-amber-50 w-1/3 border rounded p-4 shadow-2xl">
        <h1 className="text-3xl text-gray-800 p-0.5">Email Sender</h1>
        <p className="text-gray-700">
          Send Email&apos;s to any person with your own app...
        </p>
        <form action="">
          <div className="input_field mt-4 text-black">
            To:
            <label className="input validator bg-white text-black border-black">
              <svg
                className="h-[1em] opacity-50"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
              >
                <g
                  strokeLinejoin="round"
                  strokeLinecap="round"
                  strokeWidth="2.5"
                  fill="none"
                  stroke="currentColor"
                >
                  <rect width="20" height="16" x="2" y="4" rx="2"></rect>
                  <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path>
                </g>
              </svg>
              <input type="email" placeholder="mail@site.com" required />
            </label>
            <div className="validator-hint hidden">
              Enter valid email address
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EmailSender;
