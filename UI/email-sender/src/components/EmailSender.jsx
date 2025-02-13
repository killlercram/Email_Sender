// eslint-disable-next-line no-unused-vars
import React, { useState,useRef } from "react";
import toast from "react-hot-toast";
import { sendEmail } from "../services/email.service";
import { Editor } from '@tinymce/tinymce-react';
 function EmailSender() {
  // connecting with api
  const [emailData, setEmailData] = useState({
    to: "",
    subject: "",
    message: "",
  });
  //for loader
  const [sending,setSending]=useState(false);

  //For the text editor
  const editorRef = useRef(null);

  //This will track the name entered and change the name accordingly
   function handleFieldChange(event, name) {
    setEmailData({ ...emailData, [name]: event.target.value });
  }

  async function handleSubmit(event) {
    event.preventDefault(); //stopping the default action
    // console.log(emailData);
    // const email = emailData.to;
    // if(validator.isEmail(email)){
    //   return "Valid Email";
    // }else{
    //   return "Enter Valid Email;";
    // }
    if(emailData.to==''||emailData.subject==''||emailData.message==''){
      toast.error("Invalid Input!!");
      return;
    }

    //sending Email using api
    try{
      setSending(true);
      await sendEmail(emailData);
      toast.success("Email Send Successfully!!");
      setEmailData(() => ({
        to: "",
        subject: "",
        message: "",
      }));
      // document.querySelector("input[name='to']").value = "";
      // document.querySelector("input[name='subject']").value = "";
      // if (editorRef.current) {
      //   editorRef.current.setContent("");
      // }
    }catch(error){
      console.log(error);
      toast.error("Email Not Sent !!");
    }
    finally{
      setSending(false);
    }
  }

  return (
    <div className="w-full min-h-screen flex justify-center items-center">
      <div className="email_card bg-white -mt-10 w-1/2 rounded-2xl p-4 shadow-2xl">
        <h1 className="text-3xl text-gray-800 p-0.5">Email Sender</h1>
        <p className="text-gray-700">
          Send Email&apos;s to any person with your own app...
        </p>
        <form action="" onSubmit={handleSubmit}>
          {/* For to */}
          <div className="input_field mt-4 w-full">
            <label className="block mb-0.5">To:</label>
            <input
              type="email"
              placeholder="Email Id"
              className="input input-bordered w-full "
              value={emailData.to}
              onChange={(event) => handleFieldChange(event, "to")}
            />
          </div>

          {/* For the Subject */}
          <div className="input_subject mt-4">
            <label className="block mb-0.5">Subject:</label>
            <input
              type="text"
              placeholder="Type here"
              className="input input-bordered w-full "
              value={emailData.subject}
              onChange={(event) => handleFieldChange(event, "subject")}
            />
          </div>

          {/* For Text Area */}
          <div className="input_message mt-4">
            <label className="block mb-0.5">Your Message:</label>
            <textarea
              value={emailData.message}
              onChange={(event) => handleFieldChange(event, "message")}
              placeholder="Write your thoughts here..."
              rows={5}
              className="textarea textarea-bordered textarea-lg w-full "
            ></textarea>

            {/* <Editor
            onEditorChange={()=>{
              setEmailData({...emailData,'message':editorRef.current.getContent()})
            }}
             onInit={(evt, editor) => editorRef.current = editor}
             apiKey="7y09jibczu6vcialnxt4gup840zh63brxru0w5jm7ofmm30i"
             init={{
              plugins: [
                // Core editing features
                'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount',
                'checklist', 'mediaembed', 'casechange', 'export', 'formatpainter', 'pageembed', 'a11ychecker', 'tinymcespellchecker', 'permanentpen', 'powerpaste', 'advtable', 'advcode', 'editimage', 'advtemplate', 'ai', 'mentions', 'tinycomments', 'tableofcontents', 'footnotes', 'mergetags', 'autocorrect', 'typography', 'inlinecss', 'markdown','importword', 'exportword', 'exportpdf'
              ],
              toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
              tinycomments_mode: 'embedded',
              tinycomments_author: 'Author name',
              mergetags_list: [
                { value: 'First.Name', title: 'First Name' },
                { value: 'Email', title: 'Email' },
              ],
              ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),
            }}
            /> */}
            
          </div>

          {/* Loading component */}
          {sending && (<div className="loader flex justify-center flex-col items-center mt-3">
          <span className="loading loading-dots loading-md"></span>
          sending email . . .
          </div>)}

          {/* for buttons */}
          <div className="buttons mt-4 flex justify-center gap-2">
            <button
              disabled={sending}//setting to disable while sending
              type="submit"
              className="btn btn-active bg-blue-600 hover:bg-blue-800 text-white"
            >
              Send Email
            </button>
            <button type="button" onClick={()=>{setEmailData({to: "",
        subject: "",
        message: ""})}} className="btn btn-active bg-red-600 hover:bg-red-700 text-white">
              Clear
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EmailSender;
